javac ./main/*.java ./ui/*.java ./mathecollections/*.java ./fileprocessing/*.java
jar cfm Trabalho.jar Manifest.txt ./main/*.class ./ui/*.class ./mathecollections/*.class ./fileprocessing/*.class
java -jar Trabalho.jar

# para gerar o javadoc
# javadoc -d JAVADOC main fileprocessing mathecollections ui