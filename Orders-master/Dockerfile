FROM openjdk:11-jdk
EXPOSE 8083
ADD out/artifacts/bookstoreOrders_master_jar/ /bookstore/ordersService/
ENTRYPOINT ["sh", "-c", "java -jar /bookstore/ordersService/bookstoreOrders-master.jar"]
