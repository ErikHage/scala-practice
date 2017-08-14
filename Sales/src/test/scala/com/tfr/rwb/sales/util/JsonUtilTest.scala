package com.tfr.rwb.sales.util

import java.time.{LocalDate, LocalDateTime}

import com.tfr.rwb.sales.model.{SaleItem, SalesDay}
import org.specs2.mutable.Specification

import scala.io.Source

/**
  * Created by Erik Hage on 8/13/2017.
  */
class JsonUtilTest extends Specification {

  "JsonUtil.fromJson" should {
    "parse the input json to a SaleItem instance" in {
      val json: String = Source.fromResource("json/saleItem.json") getLines() mkString
      val saleItem = JsonUtil.fromJson[SaleItem](json)

      saleItem must haveClass[SaleItem]
      saleItem.productName must beEqualTo("Checks and Balances")
      saleItem.quantity must beEqualTo(1.0)
      saleItem.saleUnits must beEqualTo("Pint")
      saleItem.unitPrice must beEqualTo(6.0)
    }

//    "parse a complex json into nested case classes" in {
//      val json: String = Source.fromResource("json/sales.json") getLines() mkString
//      val salesDay = JsonUtil.fromJson[SalesDay](json)
//
//      salesDay must haveClass[SalesDay]
//      salesDay.date must beEqualTo(LocalDate.of(2017,8,1))
//      salesDay.totalSales must beEqualTo(2)
//      salesDay.sales.size must beEqualTo(2)
//      salesDay.sales.head.saleId must beEqualTo("172w-343e-rt53")
//      salesDay.sales.head.saleTime must beEqualTo(LocalDateTime.of(2017,8,1,18,15,45))
//      salesDay.sales.head.items.size must beEqualTo(2)
//      salesDay.sales.head.items.head.productName must beEqualTo("Checks and Balances")
//    }
  }

  "JsonUtil.toJson" should {
    "write the input object as a json String" in {
      val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
      val json = JsonUtil.toJson(saleItem)

      json must haveClass[String]
      json must contain("\"productName\":\"Product\"")
      json must contain("\"quantity\":1.0")
      json must contain("\"saleUnits\":\"pound\"")
      json must contain("\"unitPrice\":12.0")
    }
  }

}
