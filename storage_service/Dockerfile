FROM openjdk:11-jdk-slim
EXPOSE 8078
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/storage_service.jar"]
