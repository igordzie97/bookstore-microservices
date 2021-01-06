FROM openjdk:11-jdk-slim
EXPOSE 8083
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/orders_service.jar"]
