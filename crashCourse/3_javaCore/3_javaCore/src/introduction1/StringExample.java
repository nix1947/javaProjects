package introduction1;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class StringExample {
    public static void main(String[] args) {
        String name = "Manoj Gautam";

        // Slice the string
        name.substring(0, 3);

        // Split the string
        List<String> names =  Arrays.stream(name.split(" ")).map(String::toUpperCase).map(String::toLowerCase).toList();
        System.out.println(names);


        // Using of String tokenizer.
        // StringTokenizer implements the Iterator Interface and design patterns.
        StringTokenizer st = new StringTokenizer("Hello World of java");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }

        // Split the string with tokenizer, using delimiter
        StringTokenizer nst = new StringTokenizer("Hello, World|of| java", ", |");
        System.out.println("Printing with stringTokenizer with more than one delimiter");
        while(nst.hasMoreTokens()) {
            System.out.println(nst.nextToken());
        }

        // Split the string with tokenizer include
        // true allow to include delimiter in result
        StringTokenizer nstwtoken = new StringTokenizer("Hello, World|of|Java", ", |", true);
        System.out.println("Printing element with delimiter included");
        while(nstwtoken.hasMoreTokens()) {
            System.out.println(nstwtoken.nextToken());
        }

        // Regex are more flexible than string tokenizer.
    }
}
