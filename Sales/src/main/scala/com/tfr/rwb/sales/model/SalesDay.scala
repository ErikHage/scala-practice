package com.tfr.rwb.sales.model

import java.time.LocalDate

/**
  * Created by Erik Hage on 8/12/2017.
  */
case class SalesDay(
    date: LocalDate,
    totalSales: Int,
    sales: Seq[Sale]
)
