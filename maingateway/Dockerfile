FROM openjdk:11-jdk-slim
EXPOSE 9091
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/maingateway_service.jar"]