FROM openjdk:11-jdk-slim
EXPOSE 8082
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/baskets_service.jar"]
