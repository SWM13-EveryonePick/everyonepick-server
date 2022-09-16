FROM openjdk:11
WORKDIR app
EXPOSE 8080
LABEL maintainer="pyhpyh0670@gmail.com"
COPY ./build/libs/*.jar app.jar
CMD ["java","-jar","app.jar"]