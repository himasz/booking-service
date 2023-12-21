FROM openjdk:8

LABEL Name="Booking Service"
LABEL Description="com.statista.code"
LABEL Maintainer="himaszidan@gmail.com"
LABEL Url=""
ADD ./target/booking-service.jar booking-service.jar
EXPOSE 9090

ENTRYPOINT ["java","-jar","/booking-service.jar"]
