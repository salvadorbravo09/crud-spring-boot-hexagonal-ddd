package com.sbravoc.productshexagonal.infrastructure.config;

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.redis.lettuce.Bucket4jLettuce;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuración de infraestructura para el Rate Limiting Distribuido.
 * Esta clase configura la conexión con Redis y el gestor de "Buckets" (Cubos)
 * que almacenarán los tokens de acceso de cada usuario.
 */
@Configuration
public class RateLimitConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    /**
     * Crea el cliente nativo de Redis (Lettuce).
     * Lettuce es un cliente reactivo y thread-safe, ideal para alto rendimiento.
     *
     * @return Cliente de conexión a Redis configurado.
     */
    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(RedisURI.builder()
                .withHost(redisHost)
                .withPort(redisPort)
                .build());
    }

    /**
     * Configura el ProxyManager de Bucket4j.
     * Este componente actúa como intermediario entre la aplicación y Redis.
     * Se encarga de gestionar la persistencia de los contadores de forma atómica
     * usando operaciones CAS (Compare-And-Swap) para evitar condiciones de carrera
     * en entornos distribuidos.
     *
     * @param redisClient El cliente de conexión a Redis.
     * @return Un gestor de proxies configurado con llaves de tipo byte[].
     */
    @Bean
    public LettuceBasedProxyManager<byte[]> proxyManager(RedisClient redisClient) {
        return Bucket4jLettuce.casBasedBuilder(redisClient)
                // Estrategia de Limpieza:
                // Si un bucket ya está lleno y nadie lo usa, se borra de la memoria de Redis
                // para ahorrar recursos. "basedOnTimeForRefillingBucketUpToMax" calcula
                // automáticamente cuándo el bucket estará lleno de nuevo.
                .expirationAfterWrite(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1)))
                .build();
    }
}