package com.tfr.rwb.sales.model

import com.tfr.rwb.sales.model.ProductModel.Product
import com.tfr.rwb.sales.spec.H2Spec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

/**
  * Created by Erik on 8/21/2017.
  */
class ProductModelTest extends H2Spec("h2mem2") {

  val product = Product(None,"Checks and Balances", "Beer", "Sample", "Sample", 3.00D)
  val product2 = Product(None,"Rosie's Red Ale", "Beer", "Pint", "Pint", 6.00D)

  "ProductsRepository.insert(Product)" should "return the product with an assigned id" in {
    val result = Await.result(products.insert(product), timeout)
    result.id.isDefined shouldBe true
  }

  "ProductsRepository.find(Long)" should "return the product with the given id" in {
    val id = Await.result(products.insert(product), timeout).id.get
    val resultOption = Await.result(products.find(id), timeout)

    resultOption.isDefined shouldBe true
    resultOption.get shouldEqual product.copy(id = Some(id))
  }

  it should "return none when no product with id exists" in {
    val resultOption = Await.result(products.find(100L), timeout)
    resultOption shouldBe None
  }

  "ProductsRepository.findAll()" should "return all products inserted" in {
    Await.result(products.insert(product), timeout)
    Await.result(products.insert(product2), timeout)
    val result = Await.result(products.findAll(), timeout)

    result.size shouldEqual 2
  }

  it should "return no products when none were inserted" in {
    val result = Await.result(products.findAll(), timeout)
    result.size shouldEqual 0
  }

  "ProductsRepository.update(Product)" should "update existing product with the new values" in {
    val id = Await.result(products.insert(product), timeout).id.get
    val product2 = product.copy(id = Some(id), name = "New name", unitPrice = 5.00D)
    val updated = Await.result(products.update(product2), timeout)

    updated shouldBe true
  }

  it should "fail to update when the id doesn't exist" in {
    val updated = Await.result(products.update(product), timeout)
    updated shouldBe false
  }

  "ProductsRepository.delete(Long)" should "delete the Product with the given id" in {
    val inserted = Await.result(products.insert(product), timeout)
    val deleted = Await.result(products.delete(inserted.id.get), timeout)
    deleted shouldBe true
  }

  it should "fail to delete when the given id doesn't exist" in {
    val deleted = Await.result(products.delete(99L), timeout)
    deleted shouldBe false
  }
}
