package com.tfr.rwb.sales.model

import java.time.LocalDateTime

import com.tfr.rwb.sales.model.SaleModel.Sale
import com.tfr.rwb.sales.spec.H2Spec

/**
  * Created by Erik Hage on 8/27/2017.
  */
class SaleModelTest extends H2Spec("h2mem3") {

  val sale = Sale(None, Some(1L), LocalDateTime.of(2017, 8, 25, 12, 30, 0))
  val sale2 = Sale(None, Some(1L), LocalDateTime.of(2017, 8, 25, 12, 45, 0))



}
