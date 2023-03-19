package arrays;

import java.time.LocalDate;

public class Covar {
  static void addToArray(Object [] oa) {
    oa[oa.length - 1] = LocalDate.now();
  }
  public static void main(String[] args) {
    String [] sa = new String[4];
    addToArray(sa);
  }

}
