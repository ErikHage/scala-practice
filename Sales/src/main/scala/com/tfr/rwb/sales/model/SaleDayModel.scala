package com.tfr.rwb.sales.model

import java.time.LocalDate

import com.tfr.rwb.sales.model.SaleModel.Sale

/**
  * Created by Erik Hage on 8/12/2017.
  */
object SaleDayModel {

  case class SalesDay(
                       date: LocalDate,
                       totalSales: Int,
                       sales: Seq[Sale]
                     )

}