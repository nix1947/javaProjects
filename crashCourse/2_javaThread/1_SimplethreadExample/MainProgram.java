
public class MainProgram {

    public static void main(String[] args) {
        int totalSum = 0;
        int sum1 = 0;
        int sum2 = 0;

        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        sum2 = t2.run(1, 20000); // Thread 1
        sum1 = t1.run(20001, 1000000); // Thread 2
        totalSum = sum1 + sum2;

        System.out.println("Total sum is " + totalSum);
    }
}