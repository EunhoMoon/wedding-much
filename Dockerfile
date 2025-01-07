FROM --platform=linux/amd64 node:17.3-alpine as build-react
WORKDIR /app
COPY /frontend/package.json /frontend/package-lock.json ./
RUN npm install
COPY /frontend ./
RUN npm run build

FROM --platform=linux/amd64 amazoncorretto:17 as build-spring
WORKDIR /backend
COPY . .
COPY --from=build-react /app/build /backend/src/main/resources/static
RUN ./gradlew build

FROM --platform=linux/amd64 amazoncorretto:17
WORKDIR /app
COPY --from=build-spring /backend/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.password=${DB_PASSWORD}", "-Djwt.issuer=${JWT_ISSUER}", "-Djwt.secret_key=${JWT_SECRET_KEY}", "-jar", "app.jar"]

