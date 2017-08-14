package com.tfr.rwb.sales.model

import java.time.LocalDateTime

import io.circe.generic.JsonCodec

/**
  * Created by Erik Hage on 8/12/2017.
  */
case class Sale(saleId: String, saleTime: LocalDateTime, items: Seq[SaleItem]) {

}
