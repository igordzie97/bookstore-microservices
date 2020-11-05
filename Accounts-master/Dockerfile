FROM openjdk:11-jdk
EXPOSE 8081
ADD out/artifacts/bookstoreAccounts_master_jar/ /bookstore/accountsService/
ENTRYPOINT ["sh", "-c", "java -jar /bookstore/accountsService/bookstoreAccounts-master.jar"]
