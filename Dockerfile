






# taking the base image to run the java file 
FROM openjdk:17-slim

# setting the running path in the container where the code is saved 
WORKDIR /app

# copying the source code to my container 

COPY  src   ./src 

# make a folder in container so that i can link this data folder container to my local pc so data cannot be lost 
RUN mkdir -p /app/data



# compile all the java file into /app/out 
RUN javac -d out $(find src -name "*.java")


#command to run the whole project 
CMD ["java", "-cp", "out", "com.taskmanager.Main"]