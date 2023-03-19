package localclasses;

abstract class MyRunnable2 implements Runnable {
  String message;
  MyRunnable2(String s) {message = s;}
}

public class MyTask2 {
  public static void main(String[] args) {
    MyRunnable2 r = new MyRunnable2("Aloha"){
      @Override
      public void run() {
        System.out.println("subclass of MyRunnable, message is " + message);
      }
    };
    r.run();
  }
}

