package immutable;

import java.util.ArrayList;
import java.util.List;

public final class Student {
  private String name;
  private int grade;
  private List<String> courses;

  // List<StringBuilder> allows each StringBuilder to be mutated,
  // even if the list is immutable...
  public Student(String name, int grade, List<String> courses) {
    if (name != null && grade >= 0 && grade <= 100 && courses != null) {
      this.name = name;
      this.grade = grade;
      // copyOf will NOT duplicate a List.of, but will do so for an ArrayList
      // or other regular, mutable, list.
      this.courses = List.copyOf(courses);
    }
  }

  public List<String> getCourses() {
    return courses;
  }

  //  public void setGrade(int grade) {
//    this.grade = grade;
//  }
  public Student withGrade(int grade) {
    return new Student(this.name, grade, this.courses);
  }
}
