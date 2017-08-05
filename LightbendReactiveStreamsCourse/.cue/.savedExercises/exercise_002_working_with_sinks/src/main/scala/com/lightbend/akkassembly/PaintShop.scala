package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Source

/**
  * Created by Erik Hage on 8/3/2017.
  */
class PaintShop(colorSet: Set[Color]) {

  val colors: Source[Color, NotUsed] =
    Source.cycle{
      () => colorSet.iterator
    }

}
