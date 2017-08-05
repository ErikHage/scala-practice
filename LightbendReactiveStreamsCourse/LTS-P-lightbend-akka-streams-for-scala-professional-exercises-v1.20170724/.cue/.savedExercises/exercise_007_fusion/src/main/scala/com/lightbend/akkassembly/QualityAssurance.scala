package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow
import com.lightbend.akkassembly.QualityAssurance.CarFailedInspection

/**
  * Created by Erik Hage on 8/3/2017.
  */
class QualityAssurance {

  val decider: Supervision.Decider = {
    case _: CarFailedInspection => Supervision.Resume
    case _ => Supervision.Stop
  }

  val inspect: Flow[UnfinishedCar, Car, NotUsed] = {
    Flow[UnfinishedCar].map({
      case UnfinishedCar(Some(color), Some(engine), wheels, upgrade) if wheels.size == 4 =>
        Car(SerialNumber(), color, engine, wheels, upgrade)
      case c =>
        throw new CarFailedInspection(c)
    }).withAttributes(
      ActorAttributes.supervisionStrategy(decider)
    )
  }

}
object QualityAssurance {

  class CarFailedInspection(car: UnfinishedCar)
    extends IllegalStateException("A car failed inspection: " + car)

}
