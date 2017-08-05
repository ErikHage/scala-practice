package com.lightbend.akkassembly

import akka.Done
import akka.event.LoggingAdapter
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

/**
  * Created by Erik Hage on 8/3/2017.
  */
class Auditor {

  val count: Sink[Any, Future[Int]] =
    Sink.fold(0){
      case(sum, elem) => sum + 1
    }

  def log(implicit loggingAdapter: LoggingAdapter): Sink[Any, Future[Done]] = {
    Sink.foreach[Any]((e) => loggingAdapter.debug(s"$e"))
  }

}
