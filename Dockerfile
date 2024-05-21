# Etapa de construção
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/springdeskintegrador.jar
ENTRYPOINT ["java", "-jar", "/app/springdeskintegrador.jar"]
