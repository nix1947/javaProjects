package threading;

public class Stopper {
  private static volatile boolean stop = false;

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      while (!stop)
        ;
      System.out.println("Worker stopped!");
    }).start();
    System.out.println("Worker started");
    Thread.sleep(1000);
    stop = true;
    System.out.println("Signal set, main exiting");
  }
}

