package livelock;

import java.util.concurrent.Semaphore;

public class Live {
  private static Semaphore resources = new Semaphore(20);

  private static int obtainResources(int count) {
    if (resources.tryAcquire(count)) return count;
    else return 0;
  }

  private static void returnResources(int count) {
    resources.release(count);
  }

  private static void delay(int d) {
    try {
      Thread.sleep(d);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Runnable task = () -> {
      while (true) {
//        delay((int)(Math.random() * 50));
        // obtain resource batch one
        if(obtainResources(4) == 4) {
          delay(100); // doing initial work
          if (obtainResources(6) == 6) {
            System.out.println("success");
            System.exit(0);
            delay(200); // doing more work
            returnResources(6);
          } else {
            System.out.println("failed");
          }
          returnResources(4);
        }
      }
    };
    new Thread(task).start();
    new Thread(task).start();
  }
}
