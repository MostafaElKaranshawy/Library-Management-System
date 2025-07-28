FROM eclipse-temurin:23-jdk

COPY . /opt/app
WORKDIR /opt/app

ENTRYPOINT ["java", "-jar", "app.jar"]