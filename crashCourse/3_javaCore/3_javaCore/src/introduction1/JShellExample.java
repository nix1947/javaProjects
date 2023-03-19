package introduction1;

import java.io.*;


public class JShellExample  {
    public static void main(String[] args) throws IOException {
        long totalRecord = new BufferedReader(new FileReader("D:\\projects\\javaProjects\\crashCourse\\3_javaCore\\3_javaCore\\src\\introduction1\\index.txt")).lines().sorted().distinct().count();
        new BufferedWriter(new FileWriter("hello.txt")).write("Hello world");
        System.out.println("Total record is"+ totalRecord);
    }
}
