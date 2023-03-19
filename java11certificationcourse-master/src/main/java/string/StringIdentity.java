package string;

public class StringIdentity {
  public static void main(String[] args) {
    String s1 = "Hello";
    String s2 = "Hello";
    String s3 = "He";
    String s4 = s3 + "llo";

    System.out.println("s1 == s2? " + (s1 == s2));
    System.out.println("s1 == s4? " + (s1 == s4));
    s4 = s4.intern();
    System.out.println("s1 == s4? " + (s1 == s4));
  }
}
