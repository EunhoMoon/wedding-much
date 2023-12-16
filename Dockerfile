## Stage 1: Gradle & npm build
#FROM amazonlinux:2 as builder
#
#RUN yum -y install java-17-amazon-corretto-devel
#
#RUN curl -fsSL https://rpm.nodesource.com/setup_16.x | bash -
#RUN yum -y install nodejs
#
#WORKDIR /app
#
#COPY . .
#
#RUN ./gradlew clean build --info
#
## Stage 2 : Run Spring boot jar
#FROM amazoncorretto:17
#
#WORKDIR /app
#
#COPY --from=builder /app/build/libs/wedding-much.jar ./app.jar
#
#CMD ["java", "-jar", "app.jar"]
# Stage 1: Build the React application
FROM --platform=linux/amd64 node:17.3-alpine as build-react
WORKDIR /app
COPY /frontend/package.json /frontend/package-lock.json ./
RUN npm install
COPY /frontend ./
RUN npm run build

# Stage 2: Build the Spring application
FROM --platform=linux/amd64 amazoncorretto:17 as build-spring
WORKDIR /backend
COPY . .
# Copy built React app from the previous stage
COPY --from=build-react /app/build /backend/src/main/resources/static
RUN ./gradlew build

# Stage 3: Create the final image
FROM --platform=linux/amd64 amazoncorretto:17
WORKDIR /app
# Copy the built JAR from the build-java stage
COPY --from=build-spring /backend/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

