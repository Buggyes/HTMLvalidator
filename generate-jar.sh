javac ./Main/*.java ./UI/*.java ./MatheCollections/*.java ./FileProcessing/*.java
jar cfm Trabalho.jar Manifest.txt ./Main/*.class ./UI/*.class ./MatheCollections/*.class ./FileProcessing/*.class
java -jar Trabalho.jar