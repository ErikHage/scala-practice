package com.tfr.akkaoogle.models

import AkkaoogleSchema._
import org.squeryl.PrimitiveTypeMode._

/**
  * Created by Erik on 8/4/2017.
  */
class Product(val description: String,
              val vendorName: String,
              val basePrice: Double,
              val plusPercent: Double)
  extends Model[Product] {

  def calculatePrice: Double = basePrice + ( basePrice * plusPercent / 100 )

}
object Product {
  def findByDescription(description: String): Option[Product] = tx {
    products.where(p => p.description like description).headOption
  }
}
