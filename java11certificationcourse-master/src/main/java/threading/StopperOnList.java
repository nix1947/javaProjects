package threading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class StopperOnList {
  private static List<Boolean> stop;
  static {
//    stop = new ArrayList<>();
//    stop = Collections.synchronizedList(stop);
    stop = new CopyOnWriteArrayList<>();
  }
  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      while (stop.size() == 0 || !stop.remove(0))
        ;
      System.out.println("Worker stopped!");
    }).start();
    System.out.println("Worker started");
    Thread.sleep(1000);
    stop.add(true);
    System.out.println("Signal set, main exiting");
  }
}

