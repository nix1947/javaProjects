package introduction1;

import introduction1.domain.Apple;
import introduction1.domain.Color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LambdaMain {
    public static void main(String[] args) {
       List<Apple> appleList = Arrays.asList(
                new Apple(250, Color.GREEN),
                new Apple(253, Color.RED),
                new Apple(240, Color.GREEN),
                new Apple(250, Color.RED),
                new Apple(250, Color.GREEN),
                new Apple(250, Color.RED),
                new Apple(250, Color.GREEN)
       );

       // Sort the list




       // Filter green apples using filter function.
       //  List<Apple> greenApples = filter(appleList, (Apple a) -> a.getColor() == Color.GREEN);


        // Building stream
        Stream<String> names = Stream.of("Manoj", "Hari", "Shyam");
        // String::toUpperCase is a way of calling a function directly from String class
        names.map(String::toUpperCase).forEach(System.out::println);

        // Stream of integers
        Stream<Integer> nums = Stream.of(1,2,3,4,5);
        nums.reduce(0, Integer::sum);

        // Create stream from array
        int [] evenNumbs = {1,2,3,4,5,7};
        Arrays.stream(evenNumbs).sum();

        // Using filter in stream
        appleList.stream().filter( apple -> apple.getColor() == Color.GREEN);
        appleList.stream().map(apple -> apple.getColor().toString() + "ModifiedAppleColor");

        //


    }
}
