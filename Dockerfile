# ETAPA 1: Construcción (Build)
# Usamod una imagen de Maven con JDK 17 para compilar el proyecto
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias (para aprovechar el cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el codigo fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Runtime)
# Usamos una imagen ligera solo con JRE para correr la app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]