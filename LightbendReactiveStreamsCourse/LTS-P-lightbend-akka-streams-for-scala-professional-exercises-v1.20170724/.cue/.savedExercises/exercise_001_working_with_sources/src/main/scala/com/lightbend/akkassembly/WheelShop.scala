package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Source

/**
  * Created by Erik Hage on 8/3/2017.
  */
class WheelShop {

  val wheels: Source[Wheel, NotUsed] =
    Source.repeat(new Wheel)

}
