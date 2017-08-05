package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{FlowShape, Graph}
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge}
import com.lightbend.akkassembly.Upgrade.{DX, Sport}

/**
  * Created by Erik Hage on 8/3/2017.
  */
class UpgradeShop {

  type UCar = UnfinishedCar

  val upgradeDX: Flow[UCar, UCar, NotUsed] =
    Flow[UCar].map((c) => c.installUpgrade(DX))

  val upgradeSport: Flow[UCar, UCar, NotUsed] =
    Flow[UCar].map((c) => c.installUpgrade(Sport))

  val diamond: Graph[FlowShape[UCar, UCar], NotUsed] = GraphDSL.create() { implicit builder =>
    val split = builder.add(Balance[UCar](3))
    val merge = builder.add(Merge[UCar](3))

    import GraphDSL.Implicits._

    split ~> upgradeDX    ~> merge
    split ~> upgradeSport ~> merge
    split                 ~> merge

    FlowShape(split.in, merge.out)
  }

  val installUpgrades: Flow[UCar, UCar, NotUsed] = {
    Flow.fromGraph(diamond)
  }

}
