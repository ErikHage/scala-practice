package com.tfr.rwb.sales.model

import com.tfr.rwb.sales.model.SaleItemModel.SaleItemRow
import com.tfr.rwb.sales.spec.H2Spec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

/**
  * Created by Erik Hage on 8/25/2017.
  */
class SaleItemModelTest extends H2Spec("h2mem1") {

  val saleItem = SaleItemRow(None, Some(1L), 1L, 2.0D)
  val saleItem2 = SaleItemRow(None, Some(1L), 2L, 1.0D)

  "SaleItemRepository.insert(SaleItem)" should "return the saleItem with an assigned id" in {
    val result = Await.result(saleItems.insert(saleItem), timeout)
    result.id.isDefined shouldBe true
  }

  "SaleItemRepository.find(Long)" should "return the saleItem with the given id" in {
    val id = Await.result(saleItems.insert(saleItem), timeout).id.get
    val resultOption = Await.result(saleItems.find(id), timeout)

    resultOption.isDefined shouldBe true
    resultOption.get shouldEqual saleItem.copy(id = Some(id))
  }

  it should "return none when no product with id exists" in {
    val resultOption = Await.result(saleItems.find(100L), timeout)
    resultOption shouldBe None
  }

  "SaleItemRepository.findBySale(Long)" should "return all the saleItems with the gievn saleId" in {
    val id1 = Await.result(saleItems.insert(saleItem), timeout).id.get
    val id2 = Await.result(saleItems.insert(saleItem2), timeout).id.get
    val results = Await.result(saleItems.findBySale(1L), timeout)

    results.size shouldBe 2
    results.contains(saleItem.copy(id = Some(id1))) shouldBe true
    results.contains(saleItem2.copy(id = Some(id2))) shouldBe true
  }

  "SaleItemRepository.findAll()" should "return all saleItems inserted" in {
    Await.result(saleItems.insert(saleItem), timeout)
    Await.result(saleItems.insert(saleItem), timeout)
    Await.result(saleItems.insert(saleItem), timeout)
    val results = Await.result(saleItems.findAll(), timeout)

    results.size shouldBe 3
  }

  it should "return no items if none inserted" in {
    val results = Await.result(saleItems.findAll(), timeout)

    results.size shouldBe 0
  }

  "SaleItemRepository.update(SaleItem)" should "update existing saleItem with new values" in {
    val id = Await.result(saleItems.insert(saleItem), timeout).id
    val updatedItem = saleItem.copy(id = id, saleId = Some(3L))
    val isUpdated = Await.result(saleItems.update(updatedItem), timeout)

    isUpdated shouldBe true
  }

  it should "fail to update when the id doesn't exist" in {
    val isUpdated = Await.result(saleItems.update(saleItem), timeout)

    isUpdated shouldBe false
  }

  "SaleItemRepository.delete(Long)" should "delete the saleItem with the given id" in {
    val id = Await.result(saleItems.insert(saleItem), timeout).id.get
    val deleted = Await.result(saleItems.delete(id), timeout)

    deleted shouldBe true
  }

  it should "fail to delete when the given id doesn't exist" in {
    val deleted = Await.result(saleItems.delete(1L), timeout)

    deleted shouldBe false
  }
}
