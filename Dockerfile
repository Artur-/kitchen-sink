FROM eclipse-temurin:21-jdk AS build

ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . $HOME

# Pass the offline key as a build arg:
#
#   $ docker build --build-arg offlinekey="your-key" .
#
ARG offlinekey=""

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -U clean package -DskipTests -Dvaadin.offlineKey=${offlinekey}

FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
