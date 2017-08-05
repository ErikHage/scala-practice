package com.lightbend.akkassembly

import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Keep, Sink}

import scala.concurrent.Future
import scala.collection.immutable._

/**
  * Created by Erik Hage on 8/3/2017.
  */
class Factory(bodyShop: BodyShop, paintShop: PaintShop, engineShop: EngineShop,
              wheelShop: WheelShop, qualityAssurance: QualityAssurance,
              upgradeShop: UpgradeShop)
             (implicit val materializer: Materializer) {

  def orderCars(quantity: Int): Future[Seq[Car]] = {
    bodyShop.cars
      .via(paintShop.paint)
      .via(engineShop.installEngine)
      .via(wheelShop.installWheels).async
      .via(upgradeShop.installUpgrades)
      .via(qualityAssurance.inspect)
      .take(quantity)
      .runWith(Sink.seq[Car])
  }

}
