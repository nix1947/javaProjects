import java.util.Thread;

public class MyThread implements Runnable {
    public void run(int startNum, int endNum) {
        int sum = 0;
        for(int i=startNum; i<=endNum; i++) {
            sum += i;
        }

       System.out.println("The sum is "+ sum);
        System.out.println(Thread.currentThread.getName());
    }
}
