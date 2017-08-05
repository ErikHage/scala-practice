package com.tfr.akkaoogle

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import com.tfr.akkaoogle.calculators.Messages.LowestPrice

import scala.concurrent.Future

/**
  * Created by Erik on 8/5/2017.
  */
package object calculators {

  implicit val timeout = Timeout(150, TimeUnit.MILLISECONDS)

  def findLowestPrice(futures: Iterable[Future[Option[LowestPrice]]]): Future[Option[LowestPrice]] = {

    val f: Future[Option[LowestPrice]] =
      Future.fold(futures)(Option.empty[LowestPrice]) {
        (lowestPrice: Option[LowestPrice], currentPrice: Option[LowestPrice]) => {
          currentPrice match {
            case Some(first) if lowestPrice.isEmpty => Some(first)
            case Some(c) if c.price < lowestPrice.get.price => Some(c)
            case _ => lowestPrice
          }
        }
      }
  }

}
