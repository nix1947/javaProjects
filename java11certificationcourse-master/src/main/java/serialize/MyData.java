package serialize;


import java.io.*;
import java.time.LocalDate;

class MoreData {
  @Override
  public String toString() {
    return "MoreData";
  }
}

public class MyData implements Serializable {
  private String name;
  private int count;
  private LocalDate date;
  private transient MoreData md = new MoreData();

  public MyData(String name, int count, LocalDate date) {
    System.out.println("Constructing MyData");
    this.name = name;
    this.count = count;
    this.date = date;
  }

  @Override
  public String toString() {
    return "MyData{" +
        "name='" + name + '\'' +
        ", count=" + count +
        ", date=" + date +
        ", md=" + md +
        '}';
  }

  public static void main(String[] args) throws Throwable {
    MyData md = new MyData("Fred", 99, LocalDate.now());
    try (ObjectOutputStream oos =
             new ObjectOutputStream(new FileOutputStream("data.ser"));) {
      System.out.println("Writing object: " + md);
      oos.writeObject(md);
      oos.flush();
    }

    try (ObjectInputStream ois =
             new ObjectInputStream(new FileInputStream("data.ser"))) {
      Object read = ois.readObject();
      MyData md2 = (MyData)read;
      System.out.println("Read object: " + read);
    }
  }
}
