1. Create folder structure and .java files. No need to have folder "classes" to be created beforehand as it gets auto generated on "javac -d classes" argument.
2. Inside java_revisit/cmdJarBuilder give command
   javac --source-path src src/com/java/sample/*.java
   will generate .class files in respective folders of each .java file.
3. javac --source-path src src/com/java/sample/*.java -d classes
   will generate all .class files in folder classes with appropriate folder structure.
4. In above case, no classes from notsamples will be generated as we mentioned src/com/java/sample/*.java
5. If we try
   javac --source-path src src/com/java/*.java -d classes
   Then it will generate Abc.class and all items in samples as well as there is an import com.java.sample.World;
   If there was import com.java.sample.Parent;
   then only Abc.class and Parent.class gets generated. This is because, in World, Child and Parent are imported whereas in Abc referred only Parent, it will generate only that class.
   If there was import com.java.sample.Child;
   Then due to inheritance, Parent.class and Child.class would have get generated along with Abc.class
6. If we had import com.java.notsamples.NotASample; in World.java, we would get NotASample.class as well.
7. That means, all Java classes in source-path and dependent/ imported classes of that classes are getting generated on javac.
8. So, when we don't remove unused imports, error can come at compile time as the compiler will try to find the class for the unused imports as well.
9. javac @run
  This will execute the javac with the options specified in "run" file. Here all classes related to Abc.java gets compiled.
  javac @run @classlist
  will take all options from files run and classlist. So, here all dependent classes for World.java also gets compiled.
10. If we have an aleady compiled set of classes,
   we can use,
   javac -cp classes src/com/java/Abc.java
   where -cp indicates class path where dependencies are available.
   If any of the .class files for dependencies of Abc.class is not there in classes folder, this gives error.
   Else, works successfully.
   This is useful to add dependency jars.
11. Jars:
    --------
    What:
    Java Archive
    Packaged with zip file format. ZIP uses compression algorithms. But in jar, ZIP + details like manifest etc will be there.
    Compact collection of classes and associated properties
    Why:
    Compact packing
    Portability
    Security
    Version control
    How:
    Using jar tool

    jar --create --file jarname.jar classpath
    Eg: jar --create --file abc.jar classes  
    This will include classes folder also. To make it proper, got to classes folder and then execute
    jar --create --file abc.jar .
    or
    jar cf jarname.jar classpath
    Eg: jar cf abc.jar classes  

    To list all files and folders in jar,
    use
    jar --list --file jarname.jar
    Eg: jar tf abc.jar
        jar --list --file abc.jar

   jar cf0 abc.jar classes
   will create uncompressed jar, which is larger in size

   jar xf abc.jar
   will extract content into current folder

   If we packed xyz.txt also inside jar as
   jar cfv abc.jar classes xyz.txt
   while extracting,
   jar xf abc.jar xyz.txt
   will extract only that specific file from jar.

   jar uf abc.jar Xyz.class
   will add Xyz.class into the existing jar


   Note:
   If we create a jar from outside of classes folder, it packs entire classes folder inside jar.
   To make it pack only items from com, go inside classes folder and generate jar there.
   Then try
   java -jar abc.jar
   This will give error as Main class attribute is not specified in manifest file.
   So,
   java -cp abc.jar com.java.sample.World
   if we try, will work perfectly wher cp means class path and hence jar got added to class path and it worked.
   To make it work with java -jar, we have to update manifest.mf file.


   MANIFEST.MF:
   ------------
   Generated jar file contains a folder META-INF (meta data? information of jar) which has manifest.mf
   By default it has
    Manifest-Version: 1.0
    created-By: 16 (Oracle Corporation)

  Now, what is the purpose of manifest file in meta-inf?
  This makes jar unique compared to any other zip.
  It holds all meta info required for jar to run.
  It is key: value pair type of file. (: is used to separate key and value and space after : is necessary. Else parsing error comes)

  In side classes folder;
  jar cfm abc.jar ../manifest.sample .
  will generate jar abc with custom manifest file in meta-inf folder having created-By, Manifest-Version and Main-Class.
  Note: Its mandatory to have " " space after : in sample file. Keys are represented by starting with capital separated by -.
        Manifest sample should be followed after jar name in command.
  With above jar with Main-Class,
  java -jar abc.jar will work fine.

  To add Manin-Class without sample manifest file (no m in command while creating jar) or to change existing main class,
  jar cfe abc.jar com.java.Abc .

  e = entry point

  Now,
  java -jar abc.jar
  will print content as per Abc.java

  How to add another jar as dependency in manifest?

  1. javac -cp ./libs/*.jar --source-path src src/com/java/Abc.java -d classes
  2. Copy libs into classes folder
  3. Inside classes folder
      jar cfm abc.jar ../manifest_with_logger.sample .
      This will generate a jar holding everything from classes including libs.
 4. java -jar abc.jar
      will work (error comes as logger not found, but it detects ConsoleAppender properly which we imported)
      
