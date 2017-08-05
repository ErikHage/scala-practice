package com.lightbend.akkassembly

import akka.{Done, NotUsed}
import akka.event.LoggingAdapter
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
  * Created by Erik Hage on 8/3/2017.
  */
class Auditor(implicit materializer: Materializer) {

  val count: Sink[Any, Future[Int]] =
    Sink.fold(0){
      case(sum, elem) => sum + 1
    }

  def log(implicit loggingAdapter: LoggingAdapter): Sink[Any, Future[Done]] = {
    Sink.foreach[Any]((e) => loggingAdapter.debug(s"$e"))
  }

  def sample(sampleSize: FiniteDuration): Flow[Car, Car, NotUsed] = {
    Flow[Car].takeWithin(sampleSize)
  }

  def audit(cars: Source[Car, NotUsed], sampleSize: FiniteDuration): Future[Int] = {
    cars
      .via(sample(sampleSize))
      .toMat(count)(Keep.right)
      .run()
  }

}
