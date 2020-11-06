# FROM openjdk:11-jdk
# EXPOSE 8081
# RUN file="$(ls -1 /)" && echo $file
# ADD out/artifacts/bookstoreAccounts_master_jar/ /bookstore/accountsService/
# ENTRYPOINT ["sh", "-c", "java -jar /bookstore/accountsService/bookstoreAccounts-master.jar"]



FROM maven:3.6.3-jdk-11-slim as target
WORKDIR /build
COPY src/ /build/src/
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:resolve
COPY . .
RUN mvn -B -s package -DskipTests
RUN file="$(ls -1 /build/target)" && echo $file

FROM openjdk:11-jdk
EXPOSE 8081
CMD exec java $JAVA_OPTS -jar /app/my-app.jar
COPY /target/accounts_service.jar /app/my-app.jar
