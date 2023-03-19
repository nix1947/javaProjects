import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.SourceDataLine;

public class Main {
    public static void main(String[] args) {
        // In java variable don't hold objects they only hold the references.
        // An instance method is invoked on an object, which is accessible through
        // the this reference
        // Static variables don't belong to any objects, static methods are not invoked
        // on objects
        // A record is a class with public accessor for all instance variables.
        // . An inner class is a nonstatic nested class. Its instances have a reference
        // to
        // the object of the enclosing class that constructed it

        // Working with date
        LocalDate date = LocalDate.of(2012, 5, 1);
        System.out.println(date);

        // Add day
        date = date.plusDays(2);
        System.out.println(date);
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getDayOfWeek());
        System.out.println(date.getMonth());
        System.out.println(date.getDayOfWeek().getValue());

        // Mutator and accessor
        // We say that a method is a mutator if it changes the object on which it was
        // invoked. It is an accessor if it leaves the object unchanged. The plusDays
        // method of the LocalDate class is an accessor

        // Record
        var point = new Point(1, 2);

        int x1 = point.x;
        int x2 = point.y;

        // Factory methods
        // A common use for static methods is a factory method, a static method that
        // returns new instances of the class.
        NumberFormat currencyFormater = NumberFormat.getCurrencyInstance();
        double x = 0.1;
        System.out.println("The formatted currency is" + currencyFormater.format(x));

    }

    // Classes.

    class Employee {
        // We say that a method is a mutator if it changes the object on which it was
        // invoked. It is an accessor if it leaves the object unchanged. The plusDays
        // method of the LocalDate class is an accessor

        private String name;
        // final is used for constant
        // There is no setter for finalName.
        private final String lastName;
        private double salary;

        {
            // Initialization block
            // This is not a commonly used feature. Most programmers place lengthy
            // initialization code into a helper method and invoke that method from the
            // constructors
            this.name = "";
            this.lastName = "GAUTAM";
            var randomGenerator = new Random();
            this.salary = randomGenerator.nextDouble(100.0);
        }
        // static constant
        public static final double PI;
        public static final ArrayList<Integer> expirationYear = new ArrayList<>();

        // static initialization block
        // Static initialization occurs when the class is first loaded. Like instance
        // variables, static variables are 0, false, or null unless you explicitly set
        // them to another value

        static {
            PI = 3.14;
            int year = LocalDate.now().getYear();
            for (int i = 0; i < year + 20; i++) {
                expirationYear.add(i);
            }
        }

        // constructor is public
        // This is constructor

        public Employee() {

        }

        // this is a method, if you accidentally specify a return type
        // this is a method name Employee, not a constructor!.
        public void Employee() {

        }

        public void raiseSalary(double byPercent) {
            double raise = salary * byPercent / 100;
            this.salary += raise;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return this.lastName;
        }

        // Overloading
        // method is overloaded if there are multiple versions with the same name
        // but different parameters. For example, there are overloaded versions of the
        // println method with parameters int, double, String, and so on.
        // Since you have no choice how to name a constructor, it is common to
        // overload constructors.

        // ### Calling one constructor from another.
        public Employee(double salary) {
            this(); // calling default constructor
        }

    }

    // Some people say that Java uses “call by reference” for objects. As you can
    // see from the second example, that is not true. In a language that supports
    // call by reference, a method can replace the contents of variables passed to
    // it. In Java, all parameters—object references as well as primitive type
    // values—are passed by value

    // ######## Records
    // A record is a special form of a class whose state is immutable and readable
    // by
    // the public

    // instance variables of a record are automatically final, However they may be
    // references to mutable objects
    public record Point(int x, int y) {

        // public int z = 0; // not permitted here.

        // User defined constructor
        // This is canonical constructor
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Compact constructor in record.
        // public Point {

        // }

        // Method of a record.
        public double distanceFromOrigin() {
            return Math.hypot(x, y);
        }

        // ########### Packages
        // Packages are convenient for
        // organizing your work and for separating it from code libraries provided by
        // others
        // Package gurantee the uniqueness of class names.
        // package examples, java.lang, java.util, java.math

        // In java packages do not nest. For example the packages java.util and
        // java.util.regex have nothing to do with each other. Each is its own
        // independent collection of classes.
        // To place a class in package, just declare `package` statement at the first
        // statement of the source file
        // package np.com.manojgautam
        // There is also a default package with no name
        // When class files are read from a file system, the path name needs to match
        // the
        // package name. For example, the file Employee.class must be in a
        // subdirectory com/horstmann/ corejava
        // It is a good idea to run javac with the -d option. Then the class
        // files are generated in a separate directory, without cluttering up the
        // source tree, and they have the correct subdirectory structure.

        // ### JAR command
        // archive files called JAR files
        // jar --create --versbose --file library.jar com/manojgautam/*.class
        // or with shortcut jar -c -v -f library.jar /*.class
        // with tar style jar cvf lib.jar *.class

        // ## Package a program with jaar

        // first package
        // jar -c -f program.jar -e com.manojgautam.MainClass com/oop/*.class
        // second Run program
        // java -jar program.jar

        // provide classPath
        // javac and java program have an option of -cp for class path

        // java -cp ../lib1.jar; D:/lib2.jar com.mycompany.MainClass

        // Alterntive approach is to use CLASSPATH env bariable
        // export CLASSPATH= /home/lib -- in linux
        // set CLASSPATH=:c:\ -- in window

        // When a
        // package is in a module, it is not possible to add classes to the package. All
        // packages in the Java library are grouped into modules, so you cannot access
        // the
        // Window.warningString variable simply by crafting a class in the
        // java.awt package

        // ### Import in java
        // Place import statements above the first class declaration in the source file,
        // but below the
        // package statement

        // static imports
        // A form of the import statement permits the importing of static methods and
        // variables. For example, if you add the directive
        // import static java.lang.Math.*;

    }

}
