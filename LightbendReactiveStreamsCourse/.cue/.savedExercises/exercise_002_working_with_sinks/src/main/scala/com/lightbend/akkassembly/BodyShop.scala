package com.lightbend.akkassembly

import akka.actor.Cancellable
import akka.stream.scaladsl.Source

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

/**
  * Created by Erik Hage on 8/3/2017.
  */
class BodyShop(buildTime: FiniteDuration) {

  val cars: Source[UnfinishedCar, Cancellable] =
    Source.tick(buildTime, buildTime, new UnfinishedCar)

}
