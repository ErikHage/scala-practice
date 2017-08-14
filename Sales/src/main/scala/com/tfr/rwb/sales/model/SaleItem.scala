package com.tfr.rwb.sales.model

import io.circe.generic.JsonCodec

/**
  * Created by Erik Hage on 8/12/2017.
  */
case class SaleItem(productName: String, quantity: Double, saleUnits: String, unitPrice: Double) {

}
