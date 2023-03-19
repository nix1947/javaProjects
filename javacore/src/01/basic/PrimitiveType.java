package basic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Arrays;

import java.util.Collections;

/**
 * Even though java is an object oriented programming lanaguage, not all data
 * types are
 * object in java
 * 
 * <h1>Primitives types</h1>
 * Instead, some values belong to primitive types.
 * Four of these types are signed integer types(byte, short, int, long(8 bit,
 * 16bit, 32bit and 64bit)),
 * Two are floating-point number types(float, double, 32 bit and 64 bit), one is
 * the character type char that is
 * used in the encoding for strings, and one is the
 * boolean type for truth values. so there are total 8 primitive types.
 * 
 * If the long type is not sufficient, use the BigInteger class
 * 
 * <p>
 * The ranges of the integer types do not depend on the machine on which
 * you will be running your program. After all, Java is designed as a “write
 * once,
 * run anywhere” language. In contrast, the integer types in C and C++ programs
 * depend on the processor for which a program is compiled
 * </p>
 */

public class PrimitiveType {
    public static void main(String[] args) {

        BigInteger result = computeFactorial(BigInteger.valueOf(2));
        System.out.println(result);

        var minValue = Integer.MIN_VALUE;
        var maxIntValue = Integer.MAX_VALUE;
        var floatMinValue = Float.MIN_VALUE;
        var floatMaxValue = Float.MAX_VALUE;

        System.out.println(minValue + "-" + maxIntValue + "-" + floatMaxValue + "-" + floatMinValue);

        int age = 20;
        float volume = 3.2f;
        long ageOfEarth = 100000000000L;
        short a = 2;
        byte b = 12;
        System.out.println(a);
        System.out.println(b);

        // Hexadecimal
        int myHexa = 0xF;
        System.out.println(myHexa);

        // Binary: prefix with 0b
        int myBinary = 0b11100011;
        System.out.println("Binary to decimal: " + myBinary);

        // Octal: prefix with 0, so better avoid it create confusion
        int myOctal = 012;
        System.out.println("Octal to decimal conversion " + myOctal);

        // You can add underscores to number literals, such as 1_000_000 (or
        // 0b1111_0100_0010_0100_0000) to denote one million. The underscores
        // are for human eyes only, the Java compiler simply removes them.

        long myOneMillion = 100_000_1;
        System.out.println("My one million is" + myOneMillion);

        // Floating point number with suffix f by default to double

        // 0/0: NAN
        // System.out.println(0 / 0);
        double notANumber = Double.NaN; // 0/0
        double positiveInfinity = Double.POSITIVE_INFINITY; // 1/0
        double negativeInfinity = Double.NEGATIVE_INFINITY; // -1/0

        // If you need to work with financial data and If you need precise
        // numerical computations with arbitrary precision and without roundoff errors,
        // use the BigDecimal class, introduced

        // ####### char data type########
        // char data type define the codeunits int utf-16 char
        // A code unit can be expressed in hexadecimal, with the \\u prefix.
        // \u263A is (:) chr

        // ##### variables.
        // variables are those that hold value

        // #### identifiers
        // identifiers the name of a variable, method or class is called an identifier
        // $ symbol is intended for automatically generated identifiers and you should
        // not use it
        // _(underscore)
        // before using a variable, it should initialize otherwise it will throw an
        // error
        // int a; a++ throw an error.

        // ### constants
        // final keyword is used to define constant whose value is not changed.
        // By convention uppercase letter are used for names of constant
        final int DAYS_PER_WEEK = 7;
        final int WEEKS_IN_YEAR = 52;

        boolean leapYear = true;
        final int DAYS_IN_FEBRUARY;
        if (leapYear) {
            DAYS_IN_FEBRUARY = 29;
        } else {
            DAYS_IN_FEBRUARY = 28;
        }

        // You can use enum for constant value
        // new keyword is not used creating enum values.

        enum weeks {

            SUNDAY,
            MONDAY,
            TUESDAY
        }

        weeks startDay = weeks.SUNDAY;
        System.out.println("StartDay is" + startDay.name() + startDay);

        // ######### Arithmetic operation
        // +, -, /, *, %, <<, >>, >>>, !, ~, ++, --, ||, &&, |, ==, !=, ^ <, >, <=, >=,
        // ? :
        // >> << >>>: arithmetic shift

        Math.min(2, 4);
        Math.max(4, 5);

        // The mathematical operators quietly return wrong results when a
        // computation overflows. For example, one billion times three
        // (1000000000 * 3) evaluates to -1294967296 because the largest
        // int value is just over two billion. If you call
        // Math.multiplyExact(1000000000, 3) instead, an exception is
        // generated. You can catch that exception or let the program terminate rather
        // than quietly continue with a wrong result. There are also methods
        // addExact, subtractExact, incrementExact,
        // decrementExact, negateExact, all with int and long
        // parameters.

        // Working with BigValue

        BigInteger ba = new BigInteger("987654321012345678977858858585858585858585858585858585858558");
        ba.multiply(new BigInteger("2"));
        System.out.println("Big value is" + ba);

        // To work with BigInteger and BigDecimal, you can't use basic operators
        // you use methods

        ba.multiply(new BigInteger("200"));

        // Some predefined constant with BigInteger
        BigInteger biga = BigInteger.ONE;
        System.out.println(biga);

        // floating point operation
        System.out.println(2 - 1.7); // print: 0.30000000000000004

        // To overcome this use BigDecimal
        BigDecimal subtractedResult = BigDecimal.valueOf(2).subtract(BigDecimal.valueOf(1.7));
        System.out.println("Subtraction Value using BigDecimal" + subtractedResult);

        var r1 = BigDecimal.valueOf(2,
                0).subtract(BigDecimal.valueOf(17, 1)); // n * 10^-e = 17 * 10^-1

        System.out.println("Bigdecimal value" + r1);

        BigDecimal subResult = BigDecimal.valueOf(2).subtract(BigDecimal.valueOf(1.7));
        System.out.println("Big Decimal subtraction result is " + subResult);

        // ################# String
        int myAge = 42;
        String output = myAge + "years"; // string concatenation
        System.out.println(output);

        System.out.println("Next year you will be" + (myAge + 1));

        // Combine string using join method
        String[] names = { "manoj", "gautam" };
        String combinedString = String.join("-", names);
        System.out.println(combinedString);

        // StringBuilder is more efficient
        var myStringBuilder = new java.lang.StringBuilder();

        for (String name : names) {
            myStringBuilder.append(name);
            myStringBuilder.append("*");

        }
        System.out.println(myStringBuilder);

        // String split
        String[] splittedNames = "Manoj, Gautam".split(",");
        System.out.println(splittedNames.toString());

        // String comparision
        String firstName = "manoj";
        String lastName = "gautam";
        boolean isEqualString = firstName.equals(lastName);
        System.out.println("String are" + isEqualString);

        // Never use `==` to compare string because it doesn't do the object compariaion

        String s1 = "Manoj Gautam";
        String s2 = "Manoj";

        boolean areStringEquals = s2.equals(s1.split(" ")[0]);
        System.out.println(areStringEquals);

        String greeting = "Hello World";
        String location = greeting.substring(5, 11);
        System.out.println(location);

        // Compare using ignoreCase.
        boolean areEqual = "HelloWorld".equalsIgnoreCase("helloworld");
        System.out.println(areEqual);

        // .startsWith, endsWith, contains, indexOf, replace, toUpperCase, toLowerCase,
        // strip many methods exist.

        // Code points and code units.
        // code points are the ascii/unicode numeric value of character.
        int[] codePoints = "Ahelloworldक".codePoints().toArray();
        for (var codePoint : codePoints) {
            System.out.println(codePoint);
        }

        char ka = (char) 2325;
        System.out.println("The 2325 is equivalent to" + ka);

        // In the past, strings were always internally represented in the UTF-16
        // encoding, as arrays of char values. Nowadays, String objects use a
        // byte array of ISO-8859-1 characters when possible. A future version of
        // Java may switch to using UTF-8 internally

        // ####### Text block
        // you need to escape the blackslashes here as well.
        String paragraph = """
                THis are python paragraph sentence like in python.
                मेरो नाम मनोज हो।
                """;

        System.out.println(paragraph);

        // ############### IO
        // import java.util.Scanner
        // output: System.out.println();
        // input: var in = new Scanner(System.in); in.nextLine();
        // to read single word use: in.next();
        // read integer: in.nextInt()

        // Read password don't use scanner, use Console.

        Console terminal = System.console();
        String username = terminal.readLine("User name: ");
        char[] password = terminal.readPassword();
        System.out.println("Your username is: " + username);
        System.out.println("Your password is: " + password);

        var bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            var readerData = bufferReader.readLine();
            System.out.println(readerData);

        } catch (IOException e) {
            System.out.println("Exception" + e);
        }

        // ####### Formatted output
        // use system.out.printf: to print from 333.3333333333333 to 333.33
        // The format string "%8.2f" indicates that a floating-point number is printed
        // with a field width of 8 and 2 digits of precision
        System.out.printf("%8.2f\n", 333.3333333333333);
        System.out.printf("%4.2f\n", 333.3333333333333);

        String name = "manoj";
        age = 32;

        System.out.printf("Hello, %s. Next year, you’ll be %d.\n", name, age);

        // ######### switch chase
        var seasonCode = 0;
        // Expression style switch in java
        var seasonName = switch (seasonCode) {
            case 0 -> "Spring";
            case 1 -> "Summer";
            case 2 -> "fall";
            case 3 -> "winter";
            default -> {
                System.out.println("not matched");
                yield ""; // required to produce value
            }
        };

        // statement style switch
        // Example 1

        var color = "red";
        switch (color) {
            case "red":
                System.out.println("Color is blue");
                break;
            default:
                System.out.println("Color variable has other value than red");
        }

        // Example 2
        switch (seasonCode) { // switch statement
            case 0 -> seasonName = "Spring";
            case 1 -> seasonName = "Summer";
            case 2 -> seasonName = "Fall";
            case 3 -> seasonName = "Winter";
            default -> {
                System.out.println("???");
                seasonName = "";
            }
        }

        // multiple choice value
        seasonName = "Winter";

        var codeOfSeason = switch (seasonName) {
            case "Spring", "Winter", "Summer" -> 6;
            case "Fall" -> 4;
            default -> throw new IllegalArgumentException();

        };

        System.out.println(codeOfSeason);

        // A switch expression on an integer or String always has a default
        // case since it must yield a value, no matter what the operand value is.
        // However, a case can throw an exception, as shown in the preceding

        // ########## Loops
        var generator = new Random();
        int sum = 0;
        for (int i = 1; i <= 20; i++) {
            int next = generator.nextInt(10);
            sum += next;
        }

        for (int i = 1; i <= 20; i *= 2) {
            int next = generator.nextInt(10);
            sum += next;
        }

        // you can declare multiple variables.
        // for (int i = 0, j = n - 1; i < j; i++, j--)

        // infinite for loop:
        // for(::)
        // break, continue
        // while, do-while loop

        // ########## Array and ArrayList
        // arrays are fixed
        // arraysList grow and shrink on demand. arraylist is a part of collection
        // framework.

        /// defining an array
        String[] userNames;

        // initilize an array
        names = new String[100];

        // when constructing an array and initilizing it it filled with a default value
        // numeric types and chars are filled up with default zero value
        // boolean are filled with false
        // array objects are filled with null references.

        BigInteger[] bigNumbers = new BigInteger[100];

        for (int i = 0; i < bigNumbers.length; i++) {
            bigNumbers[i] = BigInteger.valueOf(i);
        }

        // Initialize the data
        int[] primes = { 2, 3, 5, 7 };

        // reassign array value
        primes = new int[] { 1, 2 };
        System.out.println(primes);

        // It is legal to have an arrays having length 0
        int[] zeroArray = new int[0]; // declaration
        System.out.println(zeroArray);

        // ArrayList.
        // ArrayList methods, add("ram"), remove(1), size(), get(0), set(1, "hari")
        ArrayList<String> friends = new ArrayList<String>();
        friends.add("Ram");
        friends.add("shyam");

        // ArrayList initilization
        var myFriends = new ArrayList<>(
                List.of("ram", "shyam"));

        myFriends.add(1, "David");
        friends.add(1, "Kishan");
        System.out.println(friends.size());
        System.out.println(myFriends.size());

        // Looping through an array
        for (int i = 0; i < myFriends.size(); i++) {
            System.out.println(myFriends.get(i));
        }

        // In generic class, you can't use primitive types as types parameters.
        // for example ArrayList<int> is invalid
        // There are corresponding wrapper classes to work with generic
        // Integer, Byte, Short, Long, Character, Float, Double, and Boolean

        var evenNumbers = new ArrayList<Integer>();
        evenNumbers.add(2);
        System.out.println(evenNumbers.get(0));

        // conversion between primitive types and their corresponding wrapper types is
        // automatic
        // In the call to add, an Integer object holding the value 42 was
        // automatically constructed in a process called autoboxing.

        // Enhanced for loop.
        int mSum = 0;
        for (int n : evenNumbers) {
            mSum += n;
        }

        System.out.println("Enhanced for loop result is " + mSum);

        // ############ Copying arrays and Arraylist
        // Use Arrays.copyOf static method.
        System.out.println("Copying arrays and arrayslist");

        int[] copiedPrimes = Arrays.copyOf(primes, primes.length / 2);
        System.out.println(copiedPrimes.toString());

        // Arrays algorithm
        // The Arrays and Collections classes provide implementations of common
        // algorithms and array lists.

        int[] numbers = new int[10];
        ArrayList<Integer> oddNumbers = new ArrayList<Integer>();
        // fill with default values;
        Arrays.fill(numbers, 1);

        // Collection framework work on generic type arrayList.
        Collections.fill(oddNumbers, 12);
        oddNumbers.add(13);
        oddNumbers.add(14);
        // Sorting data
        Collections.sort(oddNumbers);
        Collections.max(oddNumbers);
        Collections.min(oddNumbers);
        Collections.reverse(oddNumbers);
        Collections.shuffle(oddNumbers); // randomly shuffles the elements.
        System.out.println(oddNumbers);

        // You can use parallelSort to distribute the multiple tasks for arrays but not
        // for arrayList
        Arrays.parallelSort(primes);

        // ########## Command line arguments
        System.out.println("Command line arguments");
        for (int i = 0; i < args.length; i++) {
            System.out.println("filename is " + args[0]);
        }

        // ######### Working with multidimensional array
        System.out.println("Multidimensional array");
        int[][] square = {
                { 16, 3, 2, 13 },
                { 5, 10, 11, 8 },
                { 9, 6, 7, 12 },
                { 4, 15, 14, 1 }
        };

        int[][] mySquare = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mySquare[i][j] = i + j;
            }
        }

        System.out.println(square.toString());
        System.out.println(mySquare.toString());

        // ############# Object oriented programming
    }

    // Two dimensional arrayList.
    // There are no two-dimensional array lists, but you can declare a variable of
    // type ArrayList<ArrayList<Integer>> and build up the rows
    // yourself

    // Array parameters and return value.
    // passBy value or passBy reference.

    // Exercise.
    // 1. Write a program that reads an integer and prints it in binary, octal, and
    // hexadecimal. Print the reciprocal as a hexadecimal floating-point number.

    public static void Q1() {
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        System.out.printf("Number in binary is %b", number);
        in.close();
    }

    // 2. Write a program that reads an integer angle (which may be positive or
    // negative) and normalizes it to a value between 0 and 359 degrees. Try it
    // first with the % operator, then with floorMod.

    // 3. Using only the conditional operator, write a program that reads three
    // integers and prints the largest. Repeat with Math.max.

    // 4. Write a program that prints the smallest and largest positive double
    // values. Hint: Look up Math.nextUp in the Java API.

    // 5. What happens when you cast a double to an int that is larger than the
    // largest possible int value? Try it out.

    // 6. Write a program that computes the factorial n! = 1 × 2 × ... × n, using
    // BigInteger. Compute the factorial of 1000.

    public static BigInteger computeFactorial(BigInteger n) {
        BigInteger result = BigInteger.ONE;
        while (!n.equals(BigInteger.ONE)) {
            result.multiply(n);
            n.subtract(BigInteger.ONE);
            System.out.println(n);
        }
        return result;
    }

    // 7. Write a program that reads in two integers between 0 and 4294967295,
    // stores them in int variables, and computes and displays their unsigned
    // sum, difference, product, quotient, and remainder. Do not convert them to
    // long values.
    // 8. Write a program that reads a string and prints all of its nonempty
    // substrings.
    // 9. Section 1.5.3 has an example of two strings s and t so that
    // s.equals(t) but s != t. Come up with a different example that
    // doesn’t use substring.
    // 10. Write a program that produces a random string of letters and digits by
    // generating a random long value and printing it in base 36.
    // 11. Write a program that reads a line of text and prints all characters that
    // are
    // not ASCII, together with their Unicode values.
    // 12. Write a switch expression that, when given a string with a compass
    // direction "N", "S", "E", or "W", yields an array of x- and y-offsets. For
    // example, "W" should yield new int[] { -1, 0 }.
    // 13. Write a switch statement that, when given a string with a compass
    // direction "N", "S", "E", or "W", adjusts the variables x and y. For
    // example, "W" should decrement x by 1.
    // 14. Can you use a break in a switch statement without fall through? In a
    // switch expression? Why or why not?
    // 15. Come up with a useful scenario where the fall through behavior is
    // beneficial for a switch expression or statement. Most web searches will
    // produce examples that make sense for C or C++, where execution falls
    // from case A with no action to case B. In Java, that is not significant
    // because you can use case A, B.
    // 16. A “Quine” is a program that, without reading any input or file, prints
    // its
    // own source code. Write such a program in Java, using a text block.
    // 17. The Java Development Kit includes a file src.zip with the source code
    // of the Java library. Unzip and, with your favorite text search tool, find
    // usages of the labeled break and continue sequences. Take one and
    // rewrite it without a labeled statement.
    // 18. Write a program that prints a lottery combination, picking six distinct
    // numbers between 1 and 49. To pick six distinct numbers, start with an
    // array list filled with 1...49. Pick a random index and remove the
    // element. Repeat six times. Print the result in sorted order.
    // 19. Write a program that reads a two-dimensional array of integers and
    // determines whether it is a magic square (that is, whether the sum of all
    // rows, all columns, and the diagonals is the same). Accept lines of input
    // that you break up into individual integers, and stop when the user enters a
    // blank line. For example, with the input
    // 16 3 2 13
    // 5 10 11 8
    // 9 6 7 12
    // 4 15 14 1
    // (Blank line)
    // your program should respond affirmatively.
    // 20. Write a program that stores Pascal’s triangle up to a given n in an
    // ArrayList<ArrayList<Integer>>.
    // 21. Improve the average method so that it is called with at least one
    // argument

}
