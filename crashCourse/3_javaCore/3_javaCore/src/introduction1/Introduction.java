package introduction1;

public class Introduction {
    public static void main(String[] args) {
        // Working with strings
        /**
         * Strings:
         *  sequences of characters
         *  instance of String class
         *  use substring method to extract substrings.
         *  Position are code units not code point
         *
         */

        var greeting = "Hello world";
        String welcome = "Hello manoj";
        var substring = greeting.substring(0,3); // print 0,1,2 letters
        System.out.println(substring);

        greeting.equals("hello"); // comparision

        var isEqual = "hello".substring(0,3) == "hel"; // return false be aware.

        int index = greeting.offsetByCodePoints(0, 2);

        " Hello world ".trim();
        "Helloworld".toUpperCase();
        greeting.indexOf('l');
        greeting.lastIndexOf('e');




    }
}

