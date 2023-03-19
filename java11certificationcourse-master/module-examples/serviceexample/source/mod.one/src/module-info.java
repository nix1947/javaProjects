module mod.one {
  requires mod.three;
  provides srv.ServiceIF with srvimpl.MyService;
}
