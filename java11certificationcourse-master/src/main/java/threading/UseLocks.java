package threading;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseLocks {
  public static void main(String[] args) {
    ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
    var wlk = rrwl.writeLock();
    var cond = wlk.newCondition();
    new Thread(() -> {
      System.out.println("t1 - started, taking lock");
      wlk.lock();
      try {
        System.out.println("t1 - sleeping");
        Thread.sleep(1000);
        System.out.println("t1 - starting await");
        cond.await();
        System.out.println("t1 - restarted from await");
      } catch (InterruptedException ie) {
        System.out.println("t1 - That shouldn't have happened...");
      } finally {
        System.out.println("t1 - releasing lock");
        wlk.unlock();
      }
    }).start();

    new Thread(() -> {
      System.out.println("t2 - starting, about to sleep");
      try {
        Thread.sleep(100);
      } catch (InterruptedException ie) {
        System.out.println("t2 - that shouldn't have happened...");
      }
      System.out.println("t2 - attempting to get lock");
      wlk.lock();
      try {
        System.out.println("t2 - sleeping");
        Thread.sleep(1000);
        cond.signal();
        System.out.println("t2 - cond has been signaled");
      } catch (InterruptedException ie) {
        System.out.println("t2 - That shouldn't have happened...");
      } finally {
        wlk.unlock();
      }
    }).start();
  }
}
