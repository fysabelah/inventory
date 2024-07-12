#
# Build stage
#
FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /inventory-system

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests

#
# Package stage
#
FROM amazoncorretto:21-alpine-jdk

WORKDIR /inventory-system

COPY --from=build /inventory-system/target/*.jar ./inventory.jar

EXPOSE 7072

ENTRYPOINT ["java","-jar","inventory.jar"]