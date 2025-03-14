FROM openjdk:17 AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw package -DskipTests=true

FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]