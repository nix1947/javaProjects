package locale;

import java.time.Month;
import java.util.Locale;

public class Printing {
  public static void main(String[] args) {
    String template = "In the month of %1$tB, the %2$s meteor " +
        "shower can be seen.\n" +
        "If you are patient you can expect to see %3$,12.1f shooting stars";
    Month month = Month.AUGUST;
    String showerName = "Perseid";
    System.out.printf(Locale.GERMAN, template, month, showerName, 1_000.0);
  }
}
