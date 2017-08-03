package com.tfr.scalaInAction.ch10.example.cakePattern

/**
  * Created by Erik on 8/2/2017.
  */
trait CalculatePriceServiceComponent { this: Calculators =>
  class CalculatePriceService {
    val calculators = Map(
      "costPlus" -> calculate(costPlusCalculator) _,
      "externalPriceSource" -> calculate(externalPriceSourceCalculator) _
    )

    def calculate(priceType: String, productId: String): Double = {
      if (productId.isEmpty) 0.0
      else calculators(priceType)(productId)
    }

    private[this] def calculate(c: Calculator)(productId: String): Double =
      c.calculate(productId)
  }
}

trait Calculators {
  val costPlusCalculator: CostPriceCalculator
  val externalPriceSourceCalculator: ExternalPriceSourceCalculator

  trait Calculator {
    def calculate(productId: String): Double
  }

  class CostPriceCalculator extends Calculator {
    override def calculate(productId: String) = 0.0
  }

  class ExternalPriceSourceCalculator extends Calculator {
    override def calculate(productId: String) = 0.0
  }
}

object PricingSystem extends CalculatePriceServiceComponent with Calculators {
  val costPlusCalculator = new CostPriceCalculator
  val externalPriceSourceCalculator = new ExternalPriceSourceCalculator
}

trait TestPricingSystem extends CalculatePriceServiceComponent with Calculators {
  class StubCostPlusCalculator extends CostPriceCalculator {
    override def calculate(productId: String): Double = 5.0
  }
  class StubExternalPriceSourceCalculator extends ExternalPriceSourceCalculator {
    override def calculate(productId: String): Double = 10.0
  }
  val costPlusCalculator = new StubCostPlusCalculator
  val externalPriceSourceCalculator = new StubExternalPriceSourceCalculator
}
