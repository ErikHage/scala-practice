package com.tfr.rwb.sales.parse

import java.time.LocalDate

import com.tfr.rwb.sales.model.SalesDay
import org.specs2.mutable._

import scala.io.Source

/**
  * Created by Erik Hage on 8/13/2017.
  */
class DailySalesParserTest //extends Specification
{

  val inputJson: String = Source.fromResource("json/sales.json") getLines() mkString

//  "DailySalesParser.parseSalesDay" should {
//    "parse the input json to a SalesDay instance" in {
//      val salesDay: SalesDay = DailySalesParser.parseSalesDay(inputJson)
//      salesDay must haveClass[SalesDay]
//    }
//
//    "parse the date of the sales report" in {
//      val salesDay: SalesDay = DailySalesParser.parseSalesDay(inputJson)
//
//      salesDay.date must beEqualTo(LocalDate.of(2017, 8, 1))
//    }
//
//    "parse the totalSales of the sales report" in {
//      val salesDay: SalesDay = DailySalesParser.parseSalesDay(inputJson)
//
//      salesDay.totalSales must beEqualTo(2)
//    }
//
//    "parse the individuals sales of the sales report" in {
//      val salesDay: SalesDay = DailySalesParser.parseSalesDay(inputJson)
//
//      salesDay.sales.size must beEqualTo(2)
//    }
//  }

}
