package introduction1;

import java.util.function.Predicate;
import java.util.Objects;
public class PredicateExample {
    /**
     * Predicate is a boolean function with a single argument, it returns either true or false value.
     * This predicate interface contains various method signature(and, or, negate)
     */



    Predicate<String> predicate = (s) -> s.length() > 0;

// predicate.test("foo");              // true
// predicate.negate().test("foo");     // false

Predicate<Boolean> nonNull = Objects::nonNull;
Predicate<Boolean> isNull = Objects::isNull;

Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isNotEmpty = isEmpty.negate();



}
