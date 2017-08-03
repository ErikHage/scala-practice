package com.tfr.scalaInAction.ch10.example

/**
  * Created by Erik on 8/1/2017.
  */
sealed class CalculatePriceService(
    //in constructor - simplest form of DI
    val costPlusCalculator: Calculator,
    val externalPriceSourceCalculator: Calculator
  ) {

  //without DI
  //val costPlusCalculator = new CostPlusCalculator()
  //val externalPriceSourceCalculator = new ExternalPriceSourceCalculator()

  val calculators = Map(
    "costPlus" -> calculate(costPlusCalculator) _,
    "externalPriceSource" -> calculate(externalPriceSourceCalculator) _
  )

  def calculate(priceType: String, productId: String): Double = {
    calculators(priceType)(productId)
  }

  private[this] def calculate(c: Calculator)(productId: String): Double = {
    c.calculate(productId)
  }
}

trait Calculator {
  def calculate(productId: String): Double
}

class CostPlusCalculator extends Calculator {
  override def calculate(productId: String): Double = {
    10.2
  }
}

class ExternalPriceSourceCalculator extends Calculator {
  override def calculate(productId: String): Double = {
    5.5
  }
}




