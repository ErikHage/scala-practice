package com.tfr.akkaoogle.calculators

import akka.actor.Actor
import akka.pattern.pipe

import com.tfr.akkaoogle.calculators.Messages.{FindPrice, LowestPrice}
import com.tfr.akkaoogle.models.ExternalVendor

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

/**
  * Created by Erik on 8/5/2017.
  */
class ExternalVendorProxyActor(val v: ExternalVendor) extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case fp: FindPrice =>
      var result: Option[LowestPrice] = Option.empty[LowestPrice]
      val f = Future({
        val params = "?pd=" + fp.productDescription + "&q=" + fp.quantity
        val price = Source.fromURL(v.url + params).mkString.toDouble
        Some(LowestPrice(v.name, fp.productDescription, price * fp.quantity))
      }) recover { //if the future fails with an exception, recover will return an empty Option
        case t => Option.empty[LowestPrice]
      }

      //pipeTo adds an onComplete callback to the future so that when it completes, its output can be sent to the sender.
      f pipeTo sender
  }

}
