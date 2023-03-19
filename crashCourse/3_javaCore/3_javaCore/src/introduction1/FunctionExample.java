package introduction1; import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
public class FunctionExample {
    /**
     * Functions accept one argument and produce a result. Default methods can be used to chain multiple functions together (compose, andThen)
     * `andThen` can used to chain the function andThen is only available in function
     *
     */

    public static void main(String[] args) {
        Function<String, String> toUpperCase = String::toUpperCase;

        // use `apply` to call the function
        System.out.println(toUpperCase.apply("Hello world"));

        // Create a function chain toUpperCase.toLowerCase and trim result
        Function<String, String> backToString = toUpperCase.andThen(String::toLowerCase).andThen(String::trim);

        // use apply to pass the input
        String result = backToString.apply(" Hello world ");
        System.out.println(result);

        // Lambda expression
        Function<Integer, Integer> intFunction = (Integer a) -> a * a;
        intFunction.apply(12); // return 12 * 12




    }




}
