package com.tfr.scalaInAction.ch10.example.cakePattern

import junit.framework.Assert._
import org.junit.Test

/**
  * Created by Erik on 8/2/2017.
  */
class CakePatternJUnitTest extends TestPricingSystem {

  @Test
  def shouldUseCostPlusCalculatorWhenPriceTypeIsCostPlus(): Unit = {
    val calculatePriceService = new CalculatePriceService
    val price = calculatePriceService.calculate("costPlus", "Some Product")
    assertEquals(5.0, price)
  }

  @Test
  def shouldUseExternalPriceSourceCalculator() {
    val calculatePriceService = new CalculatePriceService
    val price = calculatePriceService.calculate("externalPriceSource", "dummy")
    assertEquals(10.0D, price)
  }

}
