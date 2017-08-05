package com.tfr.akkaoogle.calculators

import akka.actor.Actor
import akka.pattern._

import com.tfr.akkaoogle.calculators.Messages.{FindPrice, LowestPrice}
import com.tfr.akkaoogle.infrastructure.{AkkaoogleActorServer => ActorServer}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Erik on 8/5/2017.
  */
class CheapestDealFinder extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case req: FindPrice =>
      val internalPrice = (ActorServer.lookup("internal-load-balancer") ? req)
        .mapTo[Option[LowestPrice]]
      val externalPrice = (ActorServer.lookup("external-load-balancer") ? req)
        .mapTo[Option[LowestPrice]] recover {
            case e: AskTimeoutException => Option.empty[LowestPrice]
      }

      val lowestPrice: Future[Option[LowestPrice]] =
        findLowestPrice(internalPrice :: externalPrice :: Nil)

      lowestPrice pipeTo sender
  }

}
