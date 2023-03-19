package threading.executors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

class MyTask implements Callable<String> {
 private static int nextId = 0;
 private int jobId = nextId++;

 @Override
 public String call() throws Exception {
   System.out.println("Job " + jobId + " starting.");
   try {
     Thread.sleep((int) (Math.random() * 2000 + 1000));
   } catch (InterruptedException ie) {
     System.out.println("Job " + jobId + " received shutdown request");
     return "Job " + jobId + " early shutdown result.";
   }
   if (Math.random() > 0.7) {
     System.out.println("Job " + jobId + " throwing exception");
     throw new SQLException("Job " + jobId + " Database broke!");
   }
   System.out.println("Job " + jobId + " completed normally.");
   return "Job " + jobId + " normal result.";
 }
}

public class Example {
 public static void main(String[] args) {
   final int JOB_COUNT = 5;
   ExecutorService es = Executors.newFixedThreadPool(2);
   List<Future<String>> handles = new ArrayList<>();

//    1 submit the jobs
    for (int i = 0; i < JOB_COUNT; i++) {
      handles.add(es.submit(new MyTask()));
    }

//    6 cancel a job that's in mid-run
    try {
      Thread.sleep(800);
      handles.get(1).cancel(true);
    } catch (InterruptedException e) {
      System.out.println("main thread interrupted??");;
    }

    es.shutdown(); // 2 shut the pool down normally--also message below!

//    es.shutdownNow(); // 3 shut the pool down forcibly

//    5 receive the results
    System.out.println("All jobs submitted to the pool");
    while (handles.size() > 0) {
      Iterator<Future<String>> ifs = handles.iterator();
      while (ifs.hasNext()) {
        Future<String> fs = ifs.next();
        if (fs.isDone()) {
          ifs.remove();
          try {
            String jobResult = fs.get();
            System.out.println("Got a job result: " + jobResult);
          } catch (InterruptedException ie) {
            // should never happen
            System.out.println("main thread interrupted??");
          } catch (ExecutionException ee) {
            System.out.println("Job threw an exception: " + ee.getCause());
          } catch (CancellationException ce) {
            System.out.println("Job was canceled!");
          }
        }
      }
    }

//    4 Wait for things to finish
    try {
      es.awaitTermination(10, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      System.out.println("main thread interrupted??");;
    }

    System.out.println("main exiting"); // 2

 }
}
