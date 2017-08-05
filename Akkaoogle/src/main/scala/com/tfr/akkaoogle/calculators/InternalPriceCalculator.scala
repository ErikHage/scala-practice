package com.tfr.akkaoogle.calculators

import akka.actor.Actor
import com.tfr.akkaoogle.calculators.Messages.{FindPrice, LowestPrice}
import com.tfr.akkaoogle.models.Product

/**
  * Created by Erik on 8/5/2017.
  */
class InternalPriceCalculator extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case FindPrice(productDescription, quantity) =>
      val price = calculatePrice(productDescription, quantity)
      sender ! price
  }

  def calculatePrice(productDescription: String, quantity: Int): Option[LowestPrice] = {
    Product.findByDescription(productDescription) map { product =>
      Some(LowestPrice(product.vendorName,
        product.description,
        product.calculatePrice * quantity))
    } getOrElse Option.empty[LowestPrice]
  }

}
