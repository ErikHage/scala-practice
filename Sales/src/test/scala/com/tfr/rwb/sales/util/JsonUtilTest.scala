package com.tfr.rwb.sales.util

import java.time.{LocalDate, LocalDateTime}

import com.tfr.rwb.sales.model.{Sale, SaleItem, SalesDay}
import com.tfr.rwb.sales.spec.UnitSpec

import scala.io.Source
import com.tfr.rwb.sales.util.CirceImplicits._

/**
  * Created by Erik Hage on 8/13/2017.
  */
class JsonUtilTest extends UnitSpec {

  "JsonUtil.fromJson" should "parse the input json to a SaleItem instance" in {
    val json: String = Source.fromResource("json/saleItem.json") getLines() mkString
    val obj = JsonUtil.fromJson[SaleItem](json)

    assert(obj.isDefined)

    val saleItem = obj.get

//      println(saleItem)

    assert(saleItem.isInstanceOf[SaleItem])
    assert(saleItem.productName == "Checks and Balances")
    assert(saleItem.quantity == 1.0)
    assert(saleItem.saleUnits == "Pint")
    assert(saleItem.unitPrice == 6.0)
  }

  it should "parse the input json to a Sale instance" in {
    val json: String = Source.fromResource("json/sale.json") getLines() mkString
    val obj = JsonUtil.fromJson[Sale](json)

    assert(obj.isDefined)
    val sale = obj.get
//      println(sale)

    sale.isInstanceOf[Sale]
  }

  it should "parse a complex json into nested case classes" in {
    val json: String = Source.fromResource("json/salesDay.json") getLines() mkString
    val obj = JsonUtil.fromJson[SalesDay](json)

    assert(obj.isDefined)
    val salesDay = obj.get
//      println(salesDay)

    assert(salesDay.isInstanceOf[SalesDay])
    assert(salesDay.date == LocalDate.of(2017,8,1))
    assert(salesDay.totalSales == 2)
    assert(salesDay.sales.size == 2)
    assert(salesDay.sales.head.saleId == "172w-343e-rt53")
    assert(salesDay.sales.head.saleTime == LocalDateTime.of(2017,8,1,18,15,45))
    assert(salesDay.sales.head.items.size == 2)
    assert(salesDay.sales.head.items.head.productName == "Checks and Balances")
  }


  "JsonUtil.toJson" should "write the input SaleItem object as a json String" in {
    val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
    val json = JsonUtil.toJson[SaleItem](saleItem)

//    println(json)

    assert(json.isInstanceOf[String])
    assert(json.contains(""""productName" : "Product""""))
    assert(json.contains(""""quantity" : 1.0"""))
    assert(json.contains(""""saleUnits" : "pound""""))
    assert(json.contains(""""unitPrice" : 12.0"""))
  }

  it should "write the input Sale object as a json String" in {
    val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
    val saleItem2 = SaleItem("Product2", 1.0, "pound", 12.0)
    val sale = Sale("xyz1-234g-rhy6", LocalDateTime.of(2017,8,8,15,4,20), Seq(saleItem, saleItem2))
    val json = JsonUtil.toJson[Sale](sale)

//    println(json)

    assert(json.isInstanceOf[String])
    assert(json.contains(""""saleId" : "xyz1-234g-rhy6""""))
    assert(json.contains(""""saleTime" : "2017-08-08 15:04:20""""))
    assert(json.contains(""""productName" : "Product""""))
    assert(json.contains(""""productName" : "Product2""""))
    assert(json.contains(""""quantity" : 1.0"""))
    assert(json.contains(""""saleUnits" : "pound""""))
    assert(json.contains(""""unitPrice" : 12.0"""))
  }

  it should "write the input SalesDay object as a json String" in {
    val saleItem = SaleItem("Product", 1.0, "pound", 12.0)
    val saleItem2 = SaleItem("Product2", 1.0, "pound", 12.0)
    val sale = Sale("xyz1-234g-rhy6", LocalDateTime.of(2017,8,8,15,4,20), Seq(saleItem, saleItem2))
    val sale2 = Sale("xyz1-234g-rhy7", LocalDateTime.of(2017,8,8,15,15,45), Seq(saleItem, saleItem2))
    val salesDay = SalesDay(LocalDate.of(2017,8,8), 2, Seq(sale, sale2))
    val json = JsonUtil.toJson[SalesDay](salesDay)

//    println(json)

    assert(json.isInstanceOf[String])
    assert(json.contains(""""date" : "2017-08-08""""))
    assert(json.contains(""""totalSales" : 2"""))
    assert(json.contains(""""saleId" : "xyz1-234g-rhy6""""))
    assert(json.contains(""""saleTime" : "2017-08-08 15:04:20""""))
    assert(json.contains(""""saleId" : "xyz1-234g-rhy7""""))
    assert(json.contains(""""productName" : "Product""""))
    assert(json.contains(""""productName" : "Product2""""))
    assert(json.contains(""""quantity" : 1.0"""))
    assert(json.contains(""""saleUnits" : "pound""""))
    assert(json.contains(""""unitPrice" : 12.0"""))
  }


}
