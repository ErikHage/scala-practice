package com.tfr.scalaInAction.ch10.example.functionalStyle

/**
  * Created by Erik on 8/2/2017.
  */
trait Calculators {
  type Calculator = String => Double

  protected val findCalculator: String => Calculator

  protected val calculate: (Calculator, String) => Double = (calculator, productId) => calculator(productId)
}

object ProductionCalculators extends Calculators {
  val costPlusCalculator: String => Double = produstId => 5.0
  val externalPriceSourceCalculator: String => Double = productId => 10.0

  override protected val findCalculator = Map(
    "costPlus" -> costPlusCalculator,
    "externalPriceSource" -> externalPriceSourceCalculator
  )

  def priceCalculator(priceType: String): String => Double = {
    val f: Calculator => String => Double = calculate.curried
    f(findCalculator(priceType))
  }
}

object TestCalculators extends Calculators {
  val costPlusCalculator: String => Double = produstId => 2.0
  val externalPriceSourceCalculator: String => Double = productId => 3.0

  override protected val findCalculator = Map(
    "costPlus" -> costPlusCalculator,
    "externalPriceSource" -> externalPriceSourceCalculator
  )

  def priceCalculator(priceType: String): String => Double = {
    val f: Calculator => String => Double = calculate.curried
    f(findCalculator(priceType))
  }
}

