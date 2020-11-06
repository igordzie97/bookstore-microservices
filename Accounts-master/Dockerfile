# FROM openjdk:11-jdk
# EXPOSE 8081
# RUN file="$(ls -1 /)" && echo $file
# ADD out/artifacts/bookstoreAccounts_master_jar/ /bookstore/accountsService/
# ENTRYPOINT ["sh", "-c", "java -jar /bookstore/accountsService/bookstoreAccounts-master.jar"]



FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests 
FROM openjdk:11-jdk-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
