FROM openjdk:14-jdk-alpine
run mkdir /app
COPY /build/libs/xbrowsersync-java.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]

