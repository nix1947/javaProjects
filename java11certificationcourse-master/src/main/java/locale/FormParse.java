package locale;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class FormParse {
  public static void main(String[] args) {
    LocalDate ld = LocalDate.of(1996, 1, 23);
    DateTimeFormatter dtf1 = DateTimeFormatter.ISO_LOCAL_DATE;
    System.out.println(dtf1.format(ld));

    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    System.out.println(dtf2.format(ld));

    String date = "25 September 2018";
    TemporalAccessor d = dtf2.parse(date);
    System.out.println(dtf1.format(d));

  }
}
