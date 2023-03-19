package privateaccess;

import java.time.LocalDate;

public class BuildMe {
  private String name;
  private LocalDate date;

  private BuildMe() {}

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private BuildMe template = new BuildMe();

    private Builder() {}

    public Builder name(String name) {
      template.name = name;
      return this;
    }

    public Builder date(LocalDate date) {
      template.date = date;
      return this;
    }

    public BuildMe build() {
      BuildMe rv = template;
      template = null; // don't modify later!
      return rv;
    }
  }

  @Override
  public String toString() {
    return "BuildMe{" +
        "name='" + name + '\'' +
        ", date=" + date + '}';
  }
}

class TryBuilder {
  public static void main(String[] args) {
    BuildMe bm = BuildMe.builder()
        .name("Fred")
        .date(LocalDate.now())
        .build();
    System.out.println(bm);
  }
}

