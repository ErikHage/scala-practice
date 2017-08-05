package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

import scala.collection.immutable.Seq

/**
  * Created by Erik Hage on 8/3/2017.
  */
class EngineShop(shipmentSize: Int) {

  val shipments: Source[Shipment, NotUsed] =
    Source.fromIterator(() => Iterator.continually(getShipment))

  val engines: Source[Engine, NotUsed] =
    shipments.mapConcat((s) => s.engines)

  val installEngine: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = {
    Flow[UnfinishedCar].zip(engines)
      .map((a) => {
        a._1.installEngine(a._2)
      })
  }

  def getShipment: Shipment = {
    Shipment(getEngines)
  }

  def getEngines: Seq[Engine] = {
    Seq.fill(shipmentSize)(Engine(SerialNumber()))
  }


}
