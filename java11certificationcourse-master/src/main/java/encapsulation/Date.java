package encapsulation;

public final class Date {
//  private int day;
//  private int month;
//  private int year;

  private int[] date;

  public Date(int [] date) {
    if (isValid(date[0], date[1], date[2])) {
      this.date = date.clone();
    } else throw new IllegalArgumentException("bad date values");
  }

  public int getDay() {
    return date[0];
  }

  public void setDay(int day) {
    if (isValid(day, date[1], date[2])) {
      date[0] = day;
    } else throw new IllegalArgumentException("bad date values");
  }

  public int[] getDateValues() {
    return date.clone(); // or Collections.unmodifiableList or similar
  }

  //  public Date(int day, int month, int year) {
//    if (isValid(day, month, year)) {
//      this.day = day;
//      this.month = month;
//      this.year = year;
//    } else throw new IllegalArgumentException("Bad values for a date");
//  }

//  public int getDay() {
//    return day;
//  }
//
//  public void setDay(int day) {
//    if (isValid(day, this.month, this.year)) {
//      this.day = day;
//    } else throw new IllegalArgumentException("Bad date values");
//  }
//
//  public int getMonth() {
//    return month;
//  }
//
//  public void setMonth(int month) {
//    this.month = month;
//  }
//
//  public int getYear() {
//    return year;
//  }
//
//  public void setYear(int year) {
//    this.year = year;
//  }

  public static boolean isValid(int day, int month, int year) {
    return day >= 1 && day <= 31 && month >= 1 && month <=12;
  }

}

//class BadDate extends Date {
//  int day;
//  public BadDate(int [] date) {
//    super(date);
//    day = date[0];
//  }
//
//  @Override
//  public void setDay(int day) {
//    this.day = day;
//  }
//
//  @Override
//  public int getDay() {
//    return day;
//  }
//}

class DemoDate {
  public static void main(String[] args) {
//    Date d = new Date(10, 12, 2021);
    int [] theDate = new int[]{10, 12, 2021};
    Date d = new Date(theDate);
    System.out.println("day is " + d.getDay());
//    d.day = -1;
//    d.setDay(-1);
    theDate[0] = -1;
    System.out.println("day is " + d.getDay());
    d.getDateValues()[0] = -99;
    System.out.println("day is " + d.getDay());
//    Date d2 = new BadDate(new int[]{1, 1, 2022});
//    System.out.println("day is " + d2.getDay());
//    d2.setDay(-100);
//    System.out.println("day is " + d2.getDay());
  }
}