package locale;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UseResourceBundle {
  public static void main(String[] args) {
//    Locale l = Locale.US;
//    Locale l = Locale.UK;
//    Locale l = Locale.CANADA_FRENCH;
    Locale l = Locale.GERMANY;

    Locale.setDefault(l);

    System.out.println("For Locale: " + l + " ----------------------");
    ResourceBundle bundle = PropertyResourceBundle.getBundle(
        "localization.MyResources");

    System.out.println("car-engine-cover: "
        + bundle.getString("car-engine-cover"));
    System.out.println("cake: " + bundle.getString("cake"));
    System.out.println("baked-item: " + bundle.getString("baked-item"));
    System.out.println("affirmation: " + bundle.getString("affirmation"));

    try {
      ResourceBundle rb = PropertyResourceBundle.getBundle("badname");
    } catch (Exception e) {
      System.out.println(
          "get of resource bundle \"badname\" failed with " + e);
    }

    try {
      bundle = PropertyResourceBundle.getBundle(
          "localization.MyResources");
      System.out.println("non-existent: " + bundle.getString("missing"));
    } catch (Exception e) {
      System.out.println("get of missing resource failed with " + e);
    }
  }
}
