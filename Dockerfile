FROM eclipse-temurin:21-alpine
RUN mkdir /opt/app
COPY target/slideshow.jar /opt/app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/slideshow.jar"]
