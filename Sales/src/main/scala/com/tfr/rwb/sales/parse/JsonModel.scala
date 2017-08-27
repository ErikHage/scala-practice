package com.tfr.rwb.sales.parse

import java.time.{LocalDate, LocalDateTime}


/**
  * Created by Erik Hage on 8/27/2017.
  */
object JsonModel {

  case class SalesDay(
                       id: Option[Long] = None,
                       date: LocalDate,
                       totalSales: Int,
                       sales: Seq[Sale]
                     )

  case class Sale(
                   id: Option[Long] = None,
                   saleDayId: Option[Long] = None,
                   saleTime: LocalDateTime,
                   items: Seq[SaleItem]
                 )

  case class SaleItem(
                       id: Option[Long] = None,
                       saleId: Option[Long] = None,
                       productId: Long,
                       quantity: Double
                     )

}
