# Use a lightweight OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar from target/ directory
COPY target/*.jar app.jar

# Expose port 8080 (optional for local use)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]
