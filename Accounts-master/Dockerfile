# FROM openjdk:11-jdk
# EXPOSE 8081
# RUN file="$(ls -1 /)" && echo $file
# ADD out/artifacts/bookstoreAccounts_master_jar/ /bookstore/accountsService/
# ENTRYPOINT ["sh", "-c", "java -jar /bookstore/accountsService/bookstoreAccounts-master.jar"]

FROM openjdk:11-jdk
EXPOSE 8081
CMD exec java $JAVA_OPTS -jar /app/my-app.jar
COPY /target/accounts_service.jar /app/my-app.jar
