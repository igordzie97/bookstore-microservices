#FROM maven:3.6.3-jdk-11-slim as target
#WORKDIR /build
#COPY src/ /build/src/
#COPY pom.xml .
#RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
#COPY . .
#RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests
#RUN file="$(ls -1 /build/target)" && echo $file

FROM openjdk:11-jre-slim-buster
EXPOSE 8082
CMD exec java $JAVA_OPTS -jar /app/my-app.jar
COPY /target/baskets_service.jar /app/my-app.jar


#FROM openjdk:11-jdk
#EXPOSE 8082
#ADD out/artifacts/bookstoreBaskets_master_jar/ /bookstore/basketsService/
#ENTRYPOINT ["sh", "-c", "java -jar /bookstore/basketsService/bookstoreBaskets-master.jar"]
