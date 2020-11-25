FROM adoptopenjdk/maven-openjdk8
ADD . /app
WORKDIR /app
RUN mvn package
ENTRYPOINT ["java", "-jar", "/app/target/oracletester-1.0-SNAPSHOT-jar-with-dependencies.jar"]