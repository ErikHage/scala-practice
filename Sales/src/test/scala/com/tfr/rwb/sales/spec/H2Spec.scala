package com.tfr.rwb.sales.spec

import com.tfr.rwb.sales.model.ProductModel.ProductsRepository
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by Erik on 8/21/2017.
  */
abstract class H2Spec extends FlatSpec
  with BeforeAndAfterEach
  with Matchers {

//  val conn = Database.forConfig("h2mem1")
  val timeout = 2 seconds
  val products = new ProductsRepository

  override def beforeEach() {
    Await.result(products.init(), timeout)
//    println("H2 schema created")
  }

  override def afterEach() {
    Await.result(products.drop(), timeout)
//    println("H2 schema dropped")
  }

}
