package com.tfr.akkaoogle.http

import com.typesafe.play.mini._
import play.api.mvc._
import play.api.mvc.Results._
import com.tfr.akkaoogle.infrastructure._
import akka.pattern.ask
import com.tfr.akkaoogle.calculators.Messages._
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Erik on 8/5/2017.
  */
object AkkaoogleApp extends com.typesafe.play.mini.Application {

  def route: PartialFunction[RequestHeader, Handler] = {
    case GET(Path("/")) => Action { request =>
      Ok(views.index()).as("text/html")
    }
    case GET(Path("/akkaoogle/search")) & QueryString(qs) => Action { request =>
      val desc = QueryString(qs, "productDescription").get.asScala
      val f =
        (AkkaoogleActorServer.lookup("cheapest-deal-finder-balancer") ?
          FindPrice(desc.head, 1)).mapTo[Option[LowestPrice]]
      val result = f.map({
        case Some(lowestPrice) => Ok(lowestPrice.toString).as("text/html")
        case _ => Ok("No price found").as("text/html")
      })
      AsyncResult(result)
    }
  }
}
