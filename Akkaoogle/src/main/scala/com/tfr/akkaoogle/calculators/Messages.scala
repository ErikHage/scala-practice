package com.tfr.akkaoogle.calculators

/**
  * Created by Erik on 8/5/2017.
  */
object Messages {

  //A request triggered by a user looking for the cheapest deal
  case class FindPrice(productDescription: String, quantity: Int)

  //Represents the response of the FindPrice message
  case class LowestPrice(vendorName: String, productDescription: String, price: Double)

  //Below used internally to track availability of external services
  case class LogTimeout(actorId: String, msg: String)
  case class FindStats(actorId: String)
  case class Stats(actorId: String, timeouts: Int)

}
