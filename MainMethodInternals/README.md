1. Why is main method significant?
   
   - When we do 
    javac MainClass
    and
    java MainClass
   - JVM finds the .class file and it tries to find out main method from that.
   That is how JVM is designed.
   - This is the reason for significance of main method.
   - From Java11,
    simply 
     
     java MainClass.java
    
    will compile and interpret the class.
   

2. Who calls main method?
   - JVM calls or invokes the main method on java ClassName.
    

3. Why is main public static void?
   - Public qualifier makes it accessible to JVM
   - Static makes it accessible without creating an instance of class. So, saves from wastage of memory for object creation, just to call main method.
   - void indicates that it will not return anything from the method. This helps the confusion of what return type should be given for this ever mandatory method.
   - the name main is mandatory as it is with which JVM identifies the starting point/ main of application. 
   - args is array of Strings that can be passed along with java MainClass call.
   - String[] or String... / var-args can be used with main method. 
   - It is a must to be with void as return type. Else runtime error comes. 
   - Why is it not having int or anything else as return type lie cpp?
        - CPP etc runs as a OS process. But a Java program is not a process 
        - It is running as a main thread of JVM process. 
        - JVM is a process. May be exit code for JVM makes sense.
        - But for a thread inside that, it is not required.
        - For a C++ program, it gets added to process table and parent process waiting for status from child process etc is there.
        - But in case of main thread, it's not added to process table or no process is waiting on it.
        - So, it is insignificant to return a status code from it.

4. Can we run a class without main?
        - We can compile without main.
        - But error comes as main not found
        - If we had a static block
            - Executes items in static block and then prints error that main not found if there was no explicit exit command inside static block.
        - Till Java 6, it was possible to run a class without main block and has explicit exit.
        - In 1.7 and 1.8, it got removed.
        - Again from 1.9 ownwards, it is possible to run class with no error without main() but having explicit exit(0).
        - May be this feature came back to help JShell where no main needed to execute something in cmd.
        - The change in 1.7 and 1.8 were the order. 1st Checking main class before class loading.
        - If class loading happens first, then static block executes first. Then only main method check happens.
5. Can we override and overload main?
        - main can be overloaded. But only default signature will be taken for actual main method.
        - main cannot be overridden. But it will be method hiding if child class has another main.
6. What to do if we want to pass space separated String as single arg.
   - Use " "
    

    java MainClass "sample arg[0]"

    will make args[0] = "sample arg[0]"
whereas
    
    java MainClass sample arg[0]

    will set args[0] = "sample"
    args[1] = "arg[0]"