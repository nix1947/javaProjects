package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInt {
//  private static int ai = 0;
//  private static volatile int ai = 0;
  private static final AtomicInteger ai = new AtomicInteger();
  public static void main(String[] args) throws InterruptedException {
    final int WORKERS = 4;
    List<Thread> lt = new ArrayList<>();
    for (int t = 0; t < WORKERS; t++) {
      Thread t1 = new Thread(() -> {
        for (int i = 0; i < 1_000_000; i++)
          ai.incrementAndGet()
//          ai++;
              ;
      });
      lt.add(t1);
      t1.start();
    }
    for (Thread t : lt) {
      t.join();
    }
    System.out.println("Total is " +
            ai.get()
//        ai
    );
  }
}
