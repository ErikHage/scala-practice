package com.tfr.rwb.sales.model

import java.time.LocalDateTime

import com.tfr.rwb.sales.model.SaleItemModel.SaleItem

import com.tfr.rwb.sales.util.Db

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/12/2017.
  */
object SaleModel {

  case class Sale(
                   id: Option[Long] = None,
                   saleDayId: Option[Long] = None,
                   saleTime: LocalDateTime,
                   items: Seq[SaleItem]
                 )

//  class Sales(tag: Tag) extends Table[Sale](tag, "sale") {
//    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
//    def saleDayId = column[Long]("saleDayId")
//    def saleTime = column[LocalDateTime]("saleTime")
//
//    def * = (id.?, saleDayId, saleTime) <> (Sale.tupled, Sale.unapply)
//  }

}
