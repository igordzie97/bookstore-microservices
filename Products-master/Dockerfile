FROM openjdk:11-jdk
EXPOSE 8084
ADD out/artifacts/bookstoreProducts_master_jar/ /bookstore/productsService/
ENTRYPOINT ["sh", "-c", "java -jar /bookstore/productsService/bookstoreProducts-master.jar"]
