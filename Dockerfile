FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY . /app
CMD ["java", "-jar", "application.jar"]
