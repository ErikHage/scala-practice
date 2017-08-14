package com.tfr.rwb.sales.model

import java.time.LocalDate

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

import com.tfr.rwb.sales.util._

/**
  * Created by Erik Hage on 8/12/2017.
  */
case class SalesDay(
//    @JsonFormat(pattern = "yyyy/MM/dd")
//    @JsonDeserialize(using = classOf[LocalDateDeserializer])
    date: LocalDate,
    totalSales: Int,
    sales: Seq[Sale]
)
