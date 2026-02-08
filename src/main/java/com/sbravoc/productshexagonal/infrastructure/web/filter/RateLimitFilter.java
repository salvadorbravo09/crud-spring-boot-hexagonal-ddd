package com.sbravoc.productshexagonal.infrastructure.web.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Supplier;

/**
 * Filtro de seguridad que implementa el patrón "Token Bucket" para limitar la tasa de peticiones.
 * <p>
 * Intercepta todas las peticiones HTTP entrantes antes de que lleguen a los controladores.
 * Identifica al cliente por su dirección IP y verifica si tiene tokens disponibles
 * en su "cubo" (Bucket) almacenado en Redis.
 */
@Component
public class RateLimitFilter implements Filter {

    private final ProxyManager<byte[]> proxyManager;

    public RateLimitFilter(ProxyManager<byte[]> proxyManager) {
        this.proxyManager = proxyManager;
    }

    /**
     * Lógica principal del filtro.
     *
     * @param servletRequest  La petición entrante.
     * @param servletResponse La respuesta saliente.
     * @param filterChain     La cadena de filtros para continuar la ejecución.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. Identificación del Cliente
        // Usamos la IP como clave única. En producción, podría ser una API Key o User ID.
        String clientIp = request.getRemoteAddr();
        String key = "rate_limit:" + clientIp;

        // 2. Definición de la Política de Límites (Lazy Configuration)
        // Solo se crea si el bucket no existe en Redis.
        // Regla: 5 peticiones (capacidad) que se rellenan cada 10 segundos.
        Supplier<BucketConfiguration> configurationLazySupplier = () -> BucketConfiguration.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(5)
                        .refillIntervally(5, Duration.ofSeconds(10))
                        .build())
                .build();

        // 3. Obtención/Creación del Bucket en Redis
        // Usamos key.getBytes() porque el ProxyManager está tipado como <byte[]>
        Bucket bucket = proxyManager.builder().build(key.getBytes(), configurationLazySupplier);

        // 4. Intento de Consumo
        // Intentamos tomar 1 token del cubo.
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            // ÉXITO: El cliente tenía tokens.
            // Agregamos un header informativo con los intentos restantes.
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));

            // Permitimos que la petición continúe hacia el Controller
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // BLOQUEO: El cubo estaba vacío.
            // Devolvemos 429 Too Many Requests inmediatamente.
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

            // Calculamos cuánto tiempo (en segundos) debe esperar el cliente para tener al menos 1 token.
            long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefillSeconds));

            // Respuesta JSON estándar de error
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": 429, \"message\": \"Has excedido el límite de peticiones. Espere "
                    + waitForRefillSeconds + " segundos.\"}");
        }
    }
}