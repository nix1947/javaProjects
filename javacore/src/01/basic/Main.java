package basic;

import java.util.Random;
import java.io.PrintStream;

class Main {
    // static keyword indicate it doesn't operate on instance
    // package: set of related class
    // In java everything is declared inside a class
    public static void main(String[] args) {

        // System.out is an object of instance class PrintStream
        System.out.println("hello world");

        // Random number
        Random random = new Random();
        int randomNum = random.nextInt(100);
        System.out.println("Random number is " + randomNum);

        /**
         *
         * // Three comments types in java
         * // single line comment
         * /* multi line comment is here
         */
        /** documentation comment */

        // Compiling and running a java program
        // 1. To compile and run java you need jdK and optional IDE
        // 2. Two steps are involved in compiling and running a java program
        // i. javac command: compiles the java source code into bytes code as .class
        // files
        // ii. bytes can run on jvm using java command
        // If a program consist a single source file, then you can skip the compilation
        // step and run the program using single command
        // java 01/basic/Main.java

        // In unix like os, you can make java code executable.

    }
}