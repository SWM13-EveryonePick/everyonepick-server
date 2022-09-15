FROM openjdk:11
LABEL maintainer="pyhpyh0670@gmail.com"
ARG JAR_FILE=everyonepick-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]