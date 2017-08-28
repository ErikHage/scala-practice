package com.tfr.rwb.sales.model

import java.time.LocalDateTime

import com.tfr.rwb.sales.model.SaleModel.Sale
import com.tfr.rwb.sales.spec.H2Spec

import scala.concurrent.Await

/**
  * Created by Erik Hage on 8/27/2017.
  */
class SaleModelTest extends H2Spec("h2mem3") {

  val sale = Sale(None, Some(1L), LocalDateTime.of(2017, 8, 25, 12, 30, 0))
  val sale2 = Sale(None, Some(1L), LocalDateTime.of(2017, 8, 25, 12, 45, 0))

  "SaleRepository.insert(Sale)" should "return the sale with an assigned id" in {
    val result = Await.result(sales.insert(sale), timeout)
    result.id.isDefined shouldBe true
  }

  "SaleRepository.find(Long)" should "return the sale with the given id" in {
    val id = Await.result(sales.insert(sale), timeout).id.get
    val resultOption = Await.result(sales.find(id), timeout)

    resultOption.isDefined shouldBe true
    resultOption.get shouldEqual sale.copy(id = Some(id))
  }



}
