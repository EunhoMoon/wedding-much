# Stage 1: Gradle & npm build
FROM amazonlinux:2 as builder

RUN yum -y install java-17-amazon-corretto-devel

RUN curl -fsSL https://rpm.nodesource.com/setup_16.x | bash -
RUN yum -y install nodejs

WORKDIR /app

COPY . .

RUN ./gradlew clean build --info

# Stage 2 : Run Spring boot jar
FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/build/libs/wedding-much.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
