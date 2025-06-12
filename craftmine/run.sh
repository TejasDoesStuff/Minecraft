mvn compile
mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
CP=$(cat classpath.txt)
java -XstartOnFirstThread -cp target/classes:$CP com.craftmine.app