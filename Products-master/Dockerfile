FROM openjdk:11-jdk-slim
EXPOSE 8084
COPY . app/
RUN cd app; ./mvnw -Dmaven.test.skip package
ENTRYPOINT ["java","-jar","/app/target/products_service.jar"]
