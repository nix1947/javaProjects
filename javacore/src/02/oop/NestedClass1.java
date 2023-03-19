/**
 * Java have two kinds of nested class with somewhat different behaviour.
 * 1. Static Nested class: inner class with static keyword
 * 2. Inner class: inner class without static keyword
 */
public class NestedClass1 {

    // this is nested class examples
    // this class is only related NestedClass1

    class Item { // Item is nested class here
        String description;
        int quantity;
        double unitPrice;

        double getPrice() {
            return this.quantity * this.unitPrice;
        }

        public Item() {
            // default constructor
        }

    }

}
