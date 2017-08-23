package com.tfr.rwb.sales.model

import com.tfr.rwb.sales.util.Db

import scala.concurrent.ExecutionContext.Implicits.global
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

  class SaleItems(tag: Tag) extends Table[SaleItem](tag, "saleItem") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def saleId = column[Long]("saleId")
    def productName = column[String]("productId")
    def quantity = column[Double]("quantity")
    def saleUnits = column[String]("saleUnits")
    def unitPrice = column[Double]("unitPrice")

    def * = (id.?, saleId.?, productName, quantity, saleUnits, unitPrice) <>
      (SaleItem.tupled, SaleItem.unapply)
  }

  val saleItems = TableQuery[SaleItems]

  class SaleItemsRepository extends Db {
    def init() = db.run(saleItems.schema.create)

    def drop() = db.run(saleItems.schema.drop)

    def insert(saleItem: SaleItem) = db
      .run(saleItems returning saleItems.map(_.id) += saleItem)
      .map(id => saleItem.copy(id = Some(id)))

    def find(id: Long) = db.run(saleItems.filter(_.id === id).result.headOption)

    def findAll() = db.run(saleItems.result)

    def update(saleItem: SaleItem) = {
      val query = for (item <- saleItems if item.id === saleItem.id)
        yield item
      db.run(query.update(saleItem)) map { _ > 0 }
    }

    def delete(id: Long) = db.run(saleItems.filter(_.id === id).delete) map { _ > 0 }
  }

}
