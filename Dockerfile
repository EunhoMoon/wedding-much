FROM --platform=linux/amd64 node:17.3-alpine as build-frontend
WORKDIR /app
COPY /frontend/package.json /frontend/package-lock.json ./
RUN npm install
COPY /frontend ./
RUN npm run build

FROM --platform=linux/amd64 amazoncorretto:17 as build-backend
WORKDIR /backend
COPY . .
COPY --from=build-frontend /app/build /backend/src/main/resources/static
RUN ./gradlew build

FROM --platform=linux/amd64 amazoncorretto:17
WORKDIR /app
COPY --from=build-backend /backend/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

