package casting;

import java.util.ArrayList;

public class PossibleCasts {
  public static void main(String[] args) {
    ArrayList al = null;
    Runnable rn = (Runnable)al;
    Runnable r = null;
    ArrayList al2 = (ArrayList)r;

//    String sl = null;
//    rn = (Runnable)sl;
//    Runnable r = null;
//    String s2 = (String)r;

  }
}
