FROM mcr.microsoft.com/java/jre:17-zulu-ubuntu
RUN mkdir /app
ADD build/libs/ParseService-0.0.1-SNAPSHOT.jar /app
WORKDIR /app/
CMD ["java", "-jar", "ParseService-0.0.1-SNAPSHOT.jar"]