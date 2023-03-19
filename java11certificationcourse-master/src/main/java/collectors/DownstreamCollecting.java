package collectors;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Student {
  private String name;
  private int percent;
  private List<String> courses;

  public Student(String name, int percent, String... courses) {
    this.name = name;
    this.percent = percent;
    this.courses = List.of(courses);
  }

  public String getName() {
    return name;
  }

  public int getPercent() {
    return percent;
  }

  public List<String> getCourses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", percent=" + percent +
        ", courses=" + courses +
        '}';
  }
}

public class DownstreamCollecting {

  public static String letterGrade(Student s) {
    int percent = s.getPercent();
    if (percent > 90) return "A";
    if (percent > 80) return "B";
    if (percent > 70) return "C";
    return "D";
  }

  public static void main(String[] args) {
    var result = Stream.of(
            new Student("Fred", 87, "Math", "Physics"),
            new Student("Jim", 77, "Art", "Geography"),
            new Student("Sheila", 98, "Math", "Physics", "Quantum Mechanics", "Astrophysics"),
            new Student("Alice", 85, "Math", "Computing", "Algorithms"),
            new Student("Mary", 79, "History"),
            new Student("John", 92, "Geography", "Stellar Cartography", "Navigation")
        )
        .collect(Collectors.groupingBy(s -> letterGrade(s),
            Collectors.mapping(s -> s.getName(), Collectors.joining(", "))));
    result.forEach((k, v) ->
        System.out.printf("Students with grade %s are %s\n", k, v));
  }
}
