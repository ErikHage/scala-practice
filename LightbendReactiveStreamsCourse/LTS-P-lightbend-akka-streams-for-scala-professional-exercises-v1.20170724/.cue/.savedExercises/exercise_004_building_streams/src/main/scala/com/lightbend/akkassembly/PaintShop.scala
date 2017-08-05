package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

/**
  * Created by Erik Hage on 8/3/2017.
  */
class PaintShop(colorSet: Set[Color]) {

  val colors: Source[Color, NotUsed] =
    Source.cycle{
      () => colorSet.iterator
    }

  val paint: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = {
    Flow[UnfinishedCar]
      .zip(colors)
      .map((a)=> {
        a._1.paint(a._2)
      })
  }

}
