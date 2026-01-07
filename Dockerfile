# Етап 1: Збірка (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Копіюємо pom.xml та завантажуємо залежності (кешування)
COPY pom.xml .
RUN mvn dependency:go-offline
# Копіюємо сирці та збираємо jar
COPY src ./src
RUN mvn clean package -DskipTests

# Етап 2: Запуск (Run)
FROM eclipse-temurin:17-jre
WORKDIR /app
# Копіюємо тільки готовий jar-файл з попереднього етапу
COPY --from=build /app/target/*.jar app.jar
# Відкриваємо порт, на якому працює Spring Boot
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]