FROM openjdk:11-jre-slim

# Copy the compiled Java files into the container
COPY target/forwarder.jar /app/forwarder.jar

# Set the working directory
WORKDIR /app

# Run the Java application
CMD ["java", "-jar", "forwarder.jar"]
