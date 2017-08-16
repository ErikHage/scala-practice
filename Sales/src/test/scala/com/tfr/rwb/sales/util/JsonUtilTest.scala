package com.tfr.rwb.sales.util

import java.time.{LocalDate, LocalDateTime}

import com.tfr.rwb.sales.model.{Sale, SaleItem, SalesDay}
import org.specs2.mutable.Specification

import scala.io.Source
import com.tfr.rwb.sales.util.CirceImplicits._

/**
  * Created by Erik Hage on 8/13/2017.
  */
class JsonUtilTest extends Specification {

  "JsonUtil.fromJson" should {
    "parse the input json to a SaleItem instance" in {
      val json: String = Source.fromResource("json/saleItem.json") getLines() mkString
      val obj = JsonUtil.fromJson[SaleItem](json)

      obj.isDefined must beTrue

      val saleItem = obj.get

      println(saleItem)

      saleItem must haveClass[SaleItem]
      saleItem.productName must beEqualTo("Checks and Balances")
      saleItem.quantity must beEqualTo(1.0)
      saleItem.saleUnits must beEqualTo("Pint")
      saleItem.unitPrice must beEqualTo(6.0)
    }

    "parse the input json to a Sale instance" in {
      val json: String = Source.fromResource("json/sale.json") getLines() mkString
      val obj = JsonUtil.fromJson[Sale](json)

      obj.isDefined must beTrue
      val sale = obj.get
      println(sale)

      sale must haveClass[Sale]
    }

    "parse a complex json into nested case classes" in {
      val json: String = Source.fromResource("json/salesDay.json") getLines() mkString
      val obj = JsonUtil.fromJson[SalesDay](json)

      obj.isDefined must beTrue
      val salesDay = obj.get
      println(salesDay)

      salesDay must haveClass[SalesDay]
      salesDay.date must beEqualTo(LocalDate.of(2017,8,1))
      salesDay.totalSales must beEqualTo(2)
      salesDay.sales.size must beEqualTo(2)
      salesDay.sales.head.saleId must beEqualTo("172w-343e-rt53")
      salesDay.sales.head.saleTime must beEqualTo(LocalDateTime.of(2017,8,1,18,15,45))
      salesDay.sales.head.items.size must beEqualTo(2)
      salesDay.sales.head.items.head.productName must beEqualTo("Checks and Balances")
    }
  }

  "JsonUtil.toJson" should {
    "write the input SaleItem object as a json String" in {
      val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
      val json = JsonUtil.toJson[SaleItem](saleItem)

      println(json)

      json must haveClass[String]
      json must contain("\"productName\" : \"Product\"")
      json must contain("\"quantity\" : 1.0")
      json must contain("\"saleUnits\" : \"pound\"")
      json must contain("\"unitPrice\" : 12.0")
    }

    "write the input Sale object as a json String" in {
      val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
      val saleItem2 = SaleItem("Product2", 1.0, "pound", 12.0)
      val sale = Sale("xyz1-234g-rhy6", LocalDateTime.of(2017,8,8,15,4,20), Seq(saleItem, saleItem2))
      val json = JsonUtil.toJson[Sale](sale)

      println(json)

      json must haveClass[String]
      json must contain("\"saleId\" : \"xyz1-234g-rhy6\"")
      json must contain("\"saleTime\" : \"2017-08-08 15:04:20\"")
      json must contain("\"productName\" : \"Product\"")
      json must contain("\"productName\" : \"Product2\"")
      json must contain("\"quantity\" : 1.0")
      json must contain("\"saleUnits\" : \"pound\"")
      json must contain("\"unitPrice\" : 12.0")
    }

    "write the input SalesDay object as a json String" in {
      val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
      val saleItem2 = SaleItem("Product2", 1.0, "pound", 12.0)
      val sale = Sale("xyz1-234g-rhy6", LocalDateTime.of(2017,8,8,15,4,20), Seq(saleItem, saleItem2))
      val sale2 = Sale("xyz1-234g-rhy7", LocalDateTime.of(2017,8,8,15,15,45), Seq(saleItem, saleItem2))
      val salesDay = SalesDay(LocalDate.of(2017,8,8), 2, Seq(sale, sale2))
      val json = JsonUtil.toJson[SalesDay](salesDay)

      println(json)

      json must haveClass[String]
      json must contain("\"date\" : \"2017-08-08\"")
      json must contain("\"totalSales\" : 2")
      json must contain("\"saleId\" : \"xyz1-234g-rhy6\"")
      json must contain("\"saleId\" : \"xyz1-234g-rhy7\"")
      json must contain("\"productName\" : \"Product\"")
      json must contain("\"productName\" : \"Product2\"")
      json must contain("\"quantity\" : 1.0")
      json must contain("\"saleUnits\" : \"pound\"")
      json must contain("\"unitPrice\" : 12.0")
    }
  }

}
