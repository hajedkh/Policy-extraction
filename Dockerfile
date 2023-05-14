FROM docker.io/openjdk:11-oracle

ADD target/solife-ods-policyValues-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "/app.jar"]
