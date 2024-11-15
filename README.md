# OneHomeConsumtionAPI
An application based in java to generate reports for properties that fit a buyer criteria.   
Info is comming from OneHome.(Disclaimer: Using one Home as Third Part API to consume data). Containerized using docker.

# Technologies Used
* Java
* Maven
* CSV
* JSON
* Docker
* HTTP

# Features
* Report CSV Report Generation (Active On Market, Sold, 14 Days Old)

## Running the app

### Run With Docker
* cd <"Project Directory">  
* docker build -t <"image name"> .  
* docker run -i -t <"image tag">
  
### Run Locally
* Publish The custom JSONUtil jar use in this project to your local mvn repository
mvn install:install-file -Dfile=${pathToJSONJar} -DgroupId=com.andre -DartifactId=JSONUtil -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
* Assemple the Jar
mvn clean install assembly:single
* Run the jar
java -jar ${pathToJarUseTheJarLabeledWithDependencies}

### Authorization

The authorization file needs be placed in src/main/resources/

name: authorization
We can get the specified API's from the oneHome website
![image](https://github.com/user-attachments/assets/0949502e-677a-4a5c-adc4-b0de645ff8cb)

### Sample Output
![image](https://github.com/user-attachments/assets/078e8158-42df-4dd5-84fd-154a2b4b75d2)


For any User input prompts press enter twice once input has been entered
And for generating the report in the docker please copy the reports from docker container to the machine you would like to view the report on


