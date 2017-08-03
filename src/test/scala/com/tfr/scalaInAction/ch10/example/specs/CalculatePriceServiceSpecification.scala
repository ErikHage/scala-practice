package com.tfr.scalaInAction.ch10.example.specs

import org.specs2.mutable._
import com.tfr.scalaInAction.ch10.example.cakePattern._

/**
  * Created by Erik on 8/2/2017.
  */
class CalculatePriceServiceSpecification extends Specification
  with TestPricingSystem {

  "Calculate price service" should {
    "calculate price for cost plus price type" in {
      val service = new CalculatePriceService
      val price: Double = service.calculate("costPlus","Some Product")
      price must beEqualTo(5.0)

      "for empty product id return 0.0" in {
        val service = new CalculatePriceService
        service.calculate("costPlus", "") must beEqualTo(0.0)
      }
    }
    "calculate price for external price source type" in {
      val service = new CalculatePriceService
      val price: Double = service.calculate("externalPriceSource","Some Product")
      price must be_==(10.0)
    }

    //data tables - must mix-in the DataTables trait
//    "cost plus price is calculated using 'cost + 20% of cost + given service charge' rule" in {
//      "cost" | "service charge" | "price" |>
//      100.0  !   4              ! 124     |
//      200.0  !   4              ! 244     |
//      0.0  !     2              ! 2       | {
//      (cost, serviceCharge, expected) =>
//      applyCostPlusBusinessRule(cost, serviceCharge) must be_==(expected)
//      }
//    }
  }

}
