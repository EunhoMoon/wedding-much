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
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

