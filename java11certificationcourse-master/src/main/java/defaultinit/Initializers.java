package defaultinit;

public class Initializers {
  static int count;
  static {
    System.out.println("value of count is " + count);
    count = 99;
  }

  public static void main(String[] args) {

  }
  static {
    System.out.println("count is now: " + count);
  }
}
