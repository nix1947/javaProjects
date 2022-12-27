package introduction1;

import java.util.function.Predicate;

public class PredicateExample {
    /**
     * Predicate is a boolean function with a single argument, it returns either true or false value.
     * This predicate interface contains various method signature(and, or, negate)
     */

    Predicate<String> predicate = (s) -> s.length() > 0;
}
