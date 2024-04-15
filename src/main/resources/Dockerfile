# Use an official OpenJDK runtime as a parent image
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY ../../../target/OnlineBank-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Make port 9090 available to the world outside this container
EXPOSE 9090

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]