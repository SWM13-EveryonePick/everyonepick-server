FROM openjdk:11
LABEL maintainer="pyhpyh0670@gmail.com"
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]