# Imports java 21 from temurin
FROM eclipse-temurin:21

# Meta data (DEPRECATED)
LABEL maintainer="javaguides.net@gmail.com"

# Define the work directory inside the container file system
WORKDIR /app

# As springboot already fully created the project as .jar we can just copy that 
COPY build/libs/VaLocaProject-0.0.1-SNAPSHOT.jar /app/VaLocaProject.jar

# Starting command for the container, here "java -jar VaLocaProject.jar"
ENTRYPOINT ["java","-jar","VaLocaProject.jar"]