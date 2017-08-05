package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

/**
  * Created by Erik Hage on 8/3/2017.
  */
class WheelShop {

  val wheels: Source[Wheel, NotUsed] =
    Source.repeat(new Wheel)

  val installWheels: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = {
    Flow[UnfinishedCar]
      .zip(wheels.grouped(4))
      .map((a) => {
        a._1.installWheels(a._2)
      })
  }

}
