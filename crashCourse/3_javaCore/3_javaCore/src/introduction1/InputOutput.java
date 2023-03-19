package introduction1;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class InputOutput {
    public InputOutput() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        // Read console input from a Scanner
        Scanner in = new Scanner(System.in);

        // Use nextLine, next, nextInt, nextDouble to read input

        int age = in.nextInt();

        System.out.println("You enter your age" + age);
        System.out.printf("You can use printf to format the output");

        // Reading a file
        Scanner inf = new Scanner(Paths.get("myfile.txt"), "utf-8");

        // Writing to a file
        PrintWriter out = new PrintWriter("myfile.txt", "UTF-8");
        out.println("Hello world");

        //  Collections are mostly about storing and accessing data,
        //whereas Streams is mostly about describing computations on data
        // The  fastest way to filter a collection (for example, to use filterApples in the previous section on a list)
        // is to convert it to a stream, process it in parallel, and then convert it back to a list\

        // USING STRING BUILDER:
        // while using + operator, compiler internally create StringBuilder and use appendMethod
        // StringBuilder are mutable, In java 5, StringBuilder splitted into StringBuffer and StringBuilder class, StringBuilder is not thread safe comparedto StringBuffer.

        String s1 = "Hello " + "World " + "from top of the world";
        System.out.println(s1);

        StringBuilder s2 = new StringBuilder("Hello world");
        s2.append("From the");
        s2.append("top of the world");

        // Get the value as string from StringBuilder
        s2.toString();

        String s11 = "MYString";
        String s12 = "YourString";
        String s3 = String.join(s11, s12);






    }


    public static class Main {

    }
}
