FROM openjdk:11-jdk
EXPOSE 8082
ADD out/artifacts/bookstoreBaskets_master_jar/ /bookstore/basketsService/
ENTRYPOINT ["sh", "-c", "java -jar /bookstore/basketsService/bookstoreBaskets-master.jar"]
