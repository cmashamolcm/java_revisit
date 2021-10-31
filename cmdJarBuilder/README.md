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

12. Java KeyStore: (JKS)
  1. Its a database of secret keys
     The keystore itself will be password protected.
     Represented by class java.security.KeyStore
     Writes keys to disk and reads it back
     It can contain
     private,
     public keys
     and public key certificates
     Java uses HSM (Hardware Security Module) to wrap and protect keys.

  2. Keytool:
     Used to add private-public key in a KeyStore or in short, it is used to manage the keys and certificates in keystore.
     Steps:
     1. keytool -genkeypair
                -alias asha-test
                -keypass asha-test-pass
                -keyalg RSA
    2. It will ask for a keystore password. Set with anything you like as password.    (line 2, 3 happens for first time use of keytool only. Later, it will be just existing password of keytool to verify.)   
    3. Reneter this keystore password
    4. Answer all followup questions (name, org name, country etc)
    5. Gets created and to list the items in KeyStore
          keytool -list

    Exporting certificate of a key pair:
    ------------------------------------
    keytool -exportcert
            -alias asha-test
            -file cert-asha-test
    will generate certificate and saves in cert-asha-test in current folder.

    Importing certificate of a key pair:
    ------------------------------------
    keytool -importcert
            file cert-asha-test
            -alias asha-test-imported

    will create new entry in keystore with alias asha-test-imported

    These certificates are generated in our own servers and the public key certificate will be shared to any client of our server so that we can verify them on all SSL communications.

    Eg: If we have a maven repo server and have to allow all users of company only to permit access,
    generate key pair, export certificate and ask all users of our orgs to import it to their keystore to access as client.

    Sealing A Jar:
    --------------
    Packing contents of a Java package together ensuring that it comes from the same source. Uses Sealing: true property in manifest file for the same with package specified. This is to ensure version consistency of all files of that package.
    When we want to use a default package class from the jar to be used outside, same package can be created in our project and then the visibility problem goes away. To protect such package protected classes from getting used outside, sealing of package of the jar is a way.
    This makes the package self-contained. 

    Signing A Jar:
    --------------
    Adding certificate of trust.
    Use jarsigner tool for that.
    If abc.jar is available in current package,

    jarsigner abc.jar alias-of-keystore-key-pair

    will regenerate the jar with alias-of-keystore-key-pair.RSA (machine readable certificate) and alias-of-keystore-key-pair.SF (signature file) in meta-inf folder of jar.
    This certificate can be used by others to verify that the jar is from trusted person if they have the ability to validate the certificate in their side.

    In MANIFEST.MF file also there will be list of file names and respective SHA-256-Digest.
    In alias-of-keystore-key-pair.SF also will be having files and digests.

    jarsigner -verify abc.jar
    will return "jar verified" message if the certificate is with the person who is verifying and is valid.

    If there was any corruption happened, it will throw error.
