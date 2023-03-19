package comparatordelegation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Student {
  private String name;
  private int percent;

  public Student(String name, int percent) {
    this.name = name;
    this.percent = percent;
  }

  public String getName() {
    return name;
  }

  public int getPercent() {
    return percent;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", percent=" + percent +
        '}';
  }
}
class Ordering {
  public static void main(String[] args) {
    List<Student> roster = new ArrayList<>(List.of(
        new Student("Fred", 68),
        new Student("Jim", 52),
        new Student("Sheila", 93),
        new Student("Bill", 66),
        new Student("Andy", 73),
        new Student("Fred", 83),
        new Student("Mary", 83)
    ));
    System.out.println("Original order");
    roster.forEach(System.out::println);
    System.out.println("Grade order");
    roster.sort(Comparator.comparingInt(s -> s.getPercent()));
    roster.forEach(System.out::println);
    System.out.println("Name order");
    roster.sort(Comparator.comparing(s -> s.getName()));
    roster.forEach(System.out::println);
    System.out.println("Name, then grade descending, order");
    roster.sort(Comparator.comparing((Student s) -> s.getName())
        .thenComparing(s -> s.getPercent(), Comparator.reverseOrder()));
    roster.forEach(System.out::println);
  }
}
