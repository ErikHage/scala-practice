package com.tfr.akkaoogle.calculators

import akka.actor.{Actor, ActorRef}
import akka.pattern._
import com.tfr.akkaoogle.calculators.Messages.{FindPrice, LogTimeout, LowestPrice}
import com.tfr.akkaoogle.infrastructure.AkkaoogleActorServer

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Erik on 8/5/2017.
  */
class ExternalPriceCalculator(val proxies: Iterable[ActorRef]) extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case FindPrice(productId, quantity) =>
      val futures = proxies map { proxy =>
        val fp = FindPrice(productId, quantity)
        (proxy ? fp).mapTo[Option[LowestPrice]] recover {
          case e: AskTimeoutException =>
            AkkaoogleActorServer.lookup("monitor") ! LogTimeout(proxy.path.name, "Timeout for " + fp)
            Option.empty[LowestPrice]
        }
      }

      val lowestPrice: Future[Option[LowestPrice]] = findLowestPrice(futures)
      val totalPrice: Future[Option[LowestPrice]] = lowestPrice.map { l =>
        l.map(p => p.copy(price = p.price + (p.price * 0.02)))
      }
      totalPrice pipeTo sender
  }

}
