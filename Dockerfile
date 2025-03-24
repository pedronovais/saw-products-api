FROM maven:3.9.4-eclipse-temurin-17 as builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/saw-products-api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
