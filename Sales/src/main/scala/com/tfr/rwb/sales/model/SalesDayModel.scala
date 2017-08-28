package com.tfr.rwb.sales.model

import java.time.LocalDate
import java.sql.{Date, Timestamp}

import com.tfr.rwb.sales.util.SlickColumnMappings

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/12/2017.
  */
object SalesDayModel {

  case class SalesDay(
                       id: Option[Long] = None,
                       date: LocalDate,
                       totalSales: Int
                     )

  class SalesDays(tag: Tag) extends Table[SalesDay](tag, "saleDay") with SlickColumnMappings {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def date = column[LocalDate]("date")(dateColumnType)
    def totalSales = column[Int]("totalSales")

    def * = (id.?, date, totalSales).<>(SalesDay.tupled, SalesDay.unapply)
  }

  val salesDays = TableQuery[SalesDays]

  class SalesDayRepository(db: Database) {
    def init() = db.run(salesDays.schema.create)
    def drop() = db.run(salesDays.schema.drop)

    def insert(salesDay: SalesDay) = db
      .run(salesDays returning salesDays.map(_.id) += salesDay)
      .map(id => salesDay.copy(id = Some(id)))

    def find(id: Long) =
      db.run(salesDays.filter(_.id === id).result.headOption)

    def findAll() = db.run(salesDays.result)

    def update(salesDay: SalesDay) = {
      val query = for (item <- salesDays if item.id === salesDay.id)
        yield item
      db.run(query.update(salesDay)) map { _ > 0 }
    }

    def delete(id: Long) = db.run(salesDays.filter(_.id === id).delete) map { _ > 0 }
  }
}