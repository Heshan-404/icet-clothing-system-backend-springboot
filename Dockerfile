FROM maven:3-eclipse-temurin-22 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-alpine
COPY --from=build /target/*.jar Clothify-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
