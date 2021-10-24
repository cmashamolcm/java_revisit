1. Create folder structure and classes
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
   then only Abc.class and Parent.class gets generated. This is because, in World, Child and Parent are imported whereas if bc referred only Parent, it will generate only that class.
   If there was import com.java.sample.Child;
   Then due to inheritance, Parent.class and Child.class would have get generated along with Abc.class
6. If we had import com.java.notsamples.NotASample; in World.java, we would get NotASample.class as well.
7. That means, all Java classes in source-path and dependent/ imported classes of that classes are getting generated on javac.
