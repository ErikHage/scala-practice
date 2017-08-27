package com.tfr.rwb.sales.model

import com.tfr.rwb.sales.model.SaleModel._

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik on 8/20/2017.
  */
object SaleItemModel {

  case class SaleItem(
                     id: Option[Long] = None,
                     saleId: Option[Long] = None,
                     productId: Long,
                     quantity: Double
                     )

  class SaleItems(tag: Tag) extends Table[SaleItem](tag, "saleItem") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def saleId = column[Long]("saleId")
    def productId = column[Long]("productId")
    def quantity = column[Double]("quantity")

    def * = (id.?, saleId.?, productId, quantity) <>
      (SaleItem.tupled, SaleItem.unapply)

    //def saleFK = foreignKey("sale_fk", saleId, sales)(_.id, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Restrict)
  }

  val saleItems = TableQuery[SaleItems]

  class SaleItemsRepository(db: Database) {
    def init() = db.run(saleItems.schema.create)
    def drop() = db.run(saleItems.schema.drop)

    def insert(saleItem: SaleItem) = db
      .run(saleItems returning saleItems.map(_.id) += saleItem)
      .map(id => saleItem.copy(id = Some(id)))

    def find(id: Long) =
      db.run(saleItems.filter(_.id === id).result.headOption)

    def findBySale(saleId: Long) =
      db.run(saleItems.filter(_.saleId === saleId).result)

    def findAll() = db.run(saleItems.result)

    def update(saleItem: SaleItem) = {
      val query = for (item <- saleItems if item.id === saleItem.id)
        yield item
      db.run(query.update(saleItem)) map { _ > 0 }
    }

    def delete(id: Long) =
      db.run(saleItems.filter(_.id === id).delete) map { _ > 0 }
  }

}
