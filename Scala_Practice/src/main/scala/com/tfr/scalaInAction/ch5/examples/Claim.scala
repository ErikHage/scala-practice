package com.tfr.scalaInAction.ch5.examples

/**
  * Created by Erik on 7/28/2017.
  */
sealed trait Claim {
  val claimId: Int
}

case class Full(claimId: Int) extends Claim
case class Partial(claimId: Int, percentage: Double) extends Claim
case class Generic(claimId: Int) extends Claim

case class Location(stateCode: Option[String], zipCode: Option[String])
case class Req(productId: String, location: Location, claim: Claim)

object Main {
  type PC = Tuple2[Req, Option[Double]]

  def handleFullClaim: PartialFunction[PC, PC] = {
    case (c@Req(id, l, Full(claimId)), basePrice)  => (c, basePrice)
  }

  def handlePartialClaim: PartialFunction[PC, PC] = {
    case (c@Req(id, l, Partial(claimId, percentage)), basePrice)  => (c, basePrice)
  }

  def handleZipCode: PartialFunction[PC, PC] = {
    case (c@Req(id, Location(_, Some(zipCode)), _), price) => (c, price)
  }

  def handleStateCode: PartialFunction[PC, PC] = {
    case (c@Req(id, Location(Some(stateCode), _), _), price) => (c, price)
  }

  def claimHandlers = handleFullClaim orElse handlePartialClaim
  def locationHandlers = handleZipCode orElse handleStateCode
  def priceCalculator: PartialFunction[PC, PC] = claimHandlers andThen locationHandlers

  def main(args: Array[String]): Unit = {

  }
}