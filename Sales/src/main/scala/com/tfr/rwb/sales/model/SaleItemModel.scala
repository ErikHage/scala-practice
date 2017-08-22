package com.tfr.rwb.sales.model

import slick.lifted.{ProvenShape, TableQuery, Tag}
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik on 8/20/2017.
  */
object SaleItemModel {

  case class SaleItem(
                     id: Option[Long] = None,
                     saleId: Option[Long] = None,
                     productName: String,
                     quantity: Double,
                     saleUnits: String,
                     unitPrice: Double
                     )

//  class SaleItems(tag: Tag) extends Table[SaleItem](tag, "saleItem") {
//    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
//    def saleId = column[Long]("saleId")
//    def productName = column[String]("productId")
//    def quantity = column[Double]("quantity")
//    def saleUnits = column[String]("saleUnits")
//    def unitPrice = column[Double]("unitPrice")
//
//    def * : ProvenShape[(Long, Long, String, Double, String, Double)] =
//      (id, saleId, productName, quantity, saleUnits, unitPrice)
//  }
//
//  val saleItems = TableQuery[SaleItem]

}
