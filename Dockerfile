# Etapa de construcción (build)
FROM maven:3.8.6-eclipse-temurin-17 as builder

WORKDIR /workspace/app

# Copiar POM primero para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa de producción
FROM eclipse-temurin:17-jre-jammy

VOLUME /tmp

# Copiar el JAR construido
COPY --from=builder /workspace/app/target/*.jar app.jar

# Variables de entorno configurables
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# Puerto expuesto
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]