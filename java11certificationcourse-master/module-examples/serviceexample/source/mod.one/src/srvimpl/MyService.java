package srvimpl;

import srv.ServiceIF;

public class MyService implements ServiceIF {
  @Override
  public String message(String s) {
    return "MyService says: Nice to hear that " + s;
  }
}
