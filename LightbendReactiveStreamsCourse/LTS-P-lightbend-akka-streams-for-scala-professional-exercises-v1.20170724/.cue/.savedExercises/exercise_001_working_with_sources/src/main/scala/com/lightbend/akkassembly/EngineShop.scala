package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Source

import scala.collection.immutable.Seq

/**
  * Created by Erik Hage on 8/3/2017.
  */
class EngineShop(shipmentSize: Int) {

  val shipments: Source[Shipment, NotUsed] =
    Source.fromIterator(() => Iterator.continually(getShipment))

  def getShipment: Shipment = {
    Shipment(getEngines)
  }

  def getEngines: Seq[Engine] = {
    Seq.fill(shipmentSize)(Engine(SerialNumber()))
  }
}
