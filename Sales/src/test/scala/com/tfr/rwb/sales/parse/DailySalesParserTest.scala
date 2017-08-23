package com.tfr.rwb.sales.parse

import java.time.LocalDate

import com.tfr.rwb.sales.model.SaleDayModel.SalesDay
import com.tfr.rwb.sales.spec.UnitSpec

import scala.io.Source

/**
  * Created by Erik Hage on 8/13/2017.
  */
class DailySalesParserTest extends UnitSpec {

  "DailySalesParser.parseSalesDay" should "parse the input json to a SalesDay instance" in {
    val inputJson: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val salesDayOption = DailySalesParser.parseSalesDay(inputJson)

    assert(salesDayOption.isInstanceOf[Some[SalesDay]])
  }

  it should "parse the date of the sales report" in {
    val inputJson: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val salesDay = DailySalesParser.parseSalesDay(inputJson).get

    assert(salesDay.date == LocalDate.of(2017, 8, 1))
  }

  it should "parse the totalSales of the sales report" in {
    val inputJson: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val salesDay = DailySalesParser.parseSalesDay(inputJson).get

    assert(salesDay.totalSales == 2)
  }

  it should "parse the individuals sales of the sales report" in {
    val inputJson: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val salesDay = DailySalesParser.parseSalesDay(inputJson).get

    assert(salesDay.sales.size == 2)
  }

  it should "parse the individuals sale items of the sales report" in {
    val inputJson: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val salesDay = DailySalesParser.parseSalesDay(inputJson).get

    assert(salesDay.sales.head.items.size == 2)
  }

}
