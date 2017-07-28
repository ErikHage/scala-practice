package com.tfr.scalaInAction.weKanban

import scalaz.http.request.Request
import scalaz.http.response.{NotFound, OK, Response}
import scalaz.http.servlet.{HttpServlet, HttpServletRequest, ServletApplication, StreamStreamServletApplication}
import scalaz.http.Slinky._

/**
  * Created by Erik on 7/27/2017.
  */
final class WeKanbanApplication extends StreamStreamServletApplication {

  val application = new ServletApplication[Stream, Stream] {

    def application(implicit servlet: HttpServlet, servletRequest: HttpServletRequest, request: Request[Stream]) = {

      def found(x: Iterator[Byte]): Response[Stream] = OK << x.toStream

      HttpServlet.resource(found, NotFound.xhtml)

    }

  }

}
