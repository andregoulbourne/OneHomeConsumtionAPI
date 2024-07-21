authorization file would be located in src/main/resources/

named: authorization

![image](https://github.com/user-attachments/assets/0949502e-677a-4a5c-adc4-b0de645ff8cb)
We can get the specified API's from the oneHome website

========================================================================================================

Run With Mvn
1.Publish The custom JSONUtil jar use in this project to your local mvn repository
mvn install:install-file -Dfile=${pathToJSONJar} -DgroupId=com.andre -DartifactId=JSONUtil -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
2.Assemple the Jar
mvn clean install assembly:single
3.Run the jar
java -jar ${pathToJarUseTheJarLabeledWithDependencies}



Run With Docker
cd <"Project Directory">
docker build -t <"image name"> .
docker run -i -t <"image tag">


