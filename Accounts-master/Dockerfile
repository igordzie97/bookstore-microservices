#FROM maven:3.6.3-jdk-11-slim as target
#WORKDIR /build
#COPY src/ /build/src/
#COPY pom.xml .
#RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
#COPY . .
#RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests
#RUN file="$(ls -1 /build/target)" && echo $file

FROM openjdk:11-jre-slim-buster
EXPOSE 8081
ADD /target/accounts_service.jar /app/accounts_service.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app/accounts_service.jar" ]

#FROM openjdk:11-jdk
#EXPOSE 8081
#ADD out/artifacts/bookstoreAccounts_master_jar/ /bookstore/accountsService/
#ENTRYPOINT ["sh", "-c", "java -jar /bookstore/accountsService/bookstoreAccounts-master.jar"]
