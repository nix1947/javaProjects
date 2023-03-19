package threading;

public class ThreadEx {
  private static int i;
  public static void main(String[] args) throws InterruptedException {
    Runnable r = () -> {
      for(i = 0; i < 100; i++) {
        System.out.println(Thread.currentThread().getName() + " i is " + i);
      }
    };
    r.run();

    Thread t1 = new Thread(r, "Worker thread");
    t1.start();
    Thread t2 = new Thread(r, "Another Worker thread");
    t2.start();

    t1.join();
    t2.join();
    System.out.println("main exiting");
  }
}

