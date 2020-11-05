FROM openjdk:11-jdk
EXPOSE 8080
ADD out/artifacts/bookstoreService_jar/ /bookstore/service/
ENTRYPOINT ["sh", "-c", "java -jar /bookstore/service/bookstoreService.jar"]
