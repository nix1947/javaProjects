package generics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MisUseList {
  public static void main(String[] args) {
    List<String> names = new ArrayList<>(
        List.<String>of(/*LocalDate.now(), */"Jim"));
    List<LocalDate> ld = new ArrayList();
    names.add("Fred");
//    names.add(LocalDate.of(2021, 1, 1));
    String n1 = names.get(0);
//    String n2 = (String)names.get(1);
  }
}
