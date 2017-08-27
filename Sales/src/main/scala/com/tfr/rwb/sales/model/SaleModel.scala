package com.tfr.rwb.sales.model

import java.time.LocalDateTime

import com.tfr.rwb.sales.model.SaleItemModel._
import com.tfr.rwb.sales.util.SlickColumnMappings

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/12/2017.
  */
object SaleModel extends SlickColumnMappings {

  case class Sale(
                   id: Option[Long] = None,
                   saleDayId: Option[Long] = None,
                   saleTime: LocalDateTime
                 )

  class Sales(tag: Tag) extends Table[Sale](tag, "sale") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def saleDayId = column[Long]("saleDayId")
    def saleTime = column[LocalDateTime]("saleTime")

    def * = (id.?, saleDayId.?, saleTime).<>(Sale.tupled, Sale.unapply)
  }

  val sales = TableQuery[Sales]

  class SalesRepository(db: Database) {
    def init() = db.run(sales.schema.create)
    def drop() = db.run(sales.schema.drop)

    def insert(sale: Sale) = db
      .run(sales returning sales.map(_.id) += sale)
      .map(id => sale.copy(id = Some(id)))

    def find(id: Long) =
      db.run(sales.filter(_.id === id).result.headOption)

    def findBySaleDay(saleDayId: Long) =
      db.run(sales.filter(_.saleDayId === saleDayId).result)

    def findAll() = db.run(sales.result)

    def update(sale: Sale) = {
      val query = for (item <- sales if item.id === sale.id)
        yield item
      db.run(query.update(sale)) map { _ > 0 }
    }

    def delete(id: Long) = db.run(sales.filter(_.id === id).delete) map { _ > 0 }

  }

}
