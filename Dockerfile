# Use an official OpenJDK runtime as a parent image
FROM alpine/java:22-jdk

# Set the working directory in the container
WORKDIR /app

LABEL org.opencontainers.image.title="Psicoanalisis Virtual Email" \
      org.opencontainers.image.name="Psicoanalisis Virtual Email" \
      org.opencontainers.image.description="Psicoanalisis Virtual Email Service" \
      org.opencontainers.image.authors="Alexis Mercado"

# Copy the project JAR file into the container at /app
RUN mkdir -p /app/images

COPY target/PsicoanalisisVirtualEmail-*.jar app/PsicoanalisisVirtualEmail.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app/PsicoanalisisVirtualEmail.jar"]