package com.tfr.rwb.sales.model

import com.tfr.rwb.sales.util.Db

import scala.concurrent.ExecutionContext.Implicits.global
import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/12/2017.
  */
object ProductModel {

  case class Product(
                      id: Option[Long],
                      name: String,
                      category: String,
                      subCategory: String,
                      saleUnits: String,
                      unitPrice: Double
                    )

  class Products(tag: Tag) extends Table[Product](tag, "product") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def category = column[String]("category")
    def subCategory = column[String]("subCategory")
    def saleUnits = column[String]("saleUnits")
    def unitPrice = column[Double]("unitPrice")

    def * = (id.?, name, category, subCategory, saleUnits, unitPrice) <>
      (Product.tupled, Product.unapply)
  }

  val products = TableQuery[Products]

  class ProductsRepository extends Db {

    def init() = db.run(products.schema.create)

    def drop() = db.run(products.schema.drop)

    def insert(product: Product) = db
      .run(products returning products.map(_.id) += product)
      .map(id => product.copy(id = Some(id)))

    def find(id: Long) = db.run(products.filter(_.id === id).result.headOption)

    def findAll() = db.run(products.result)

    def update(product: Product) = {
      val query = for (p <- products if p.id === product.id)
        yield p
      db.run(query.update(product)) map { _ > 0 }
    }

    def delete(id: Long) = db.run(products.filter(_.id === id).delete) map { _ > 0 }
  }

}
