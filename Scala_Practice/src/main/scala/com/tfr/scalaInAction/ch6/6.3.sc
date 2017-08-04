//Scalaz http module

//----------How it works

//wrapper over java servlet APIs

//example application trait and factory method

//trait Application[IN[_],OUT[_]] {
//  def apply(implicit req:Request[IN]): Response[OUT]
//}
//object Application {
//  def application[IN[_],OUT[_]](f: Request[IN] => Response[OUT])
//        = new Application[IN,OUT] {
//    def apply(implicit req:Request[IN]) = f(req)
//  }
//}

//see higherKindedTypes.sc for details about the [_] syntax

//how to use the application method:
//scalaz makes this a stream of bytes

//Application.application { req: Request[Stream] =>
//  new Response[Stream] {
//      ...
//  }
//}

//---------------Configuring scalaz with SBT

//"org.scalaz" %% "scalaz-core" % "6.0.3",
//"org.scalaz" %% "scalaz-http" % "6.0.3",

//---------------Building web page

//application class should extend scalaz.http.servlet.StreamStreamServletApplication trait
//one abstract method:
//def application(implicit servlet: HttpServlet,
//                servletRequest: HttpServletRequest,
//                request: Request[IN]): Response[OUT]

//see weKanban package - WeKanbanApplication