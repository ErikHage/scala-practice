package com.tfr.scalaInAction.ch12.example.remoteActor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by Erik on 8/3/2017.
 */
object MainSystem {
  class MainActor(accumulator: ActorRef) extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case "start" =>
        val urls = List("http://www.infoq.com/",
          "http://www.dzone.com/links/index.html",
          "http://www.manning.com/",
          "http://www.reddit.com/")
        accumulator ! StartCounting(urls, 2)

    }
  }

  def main(args: Array[String]): Unit = run

  private def run() = {
    val mainSystem = ActorSystem("main", ConfigFactory.load.getConfig("mainsystem"))
    val accumulator = mainSystem.actorOf(Props[WordCountMaster], name ="wordCountMaster")
    val m = mainSystem.actorOf(Props(new MainActor(accumulator)))
    m ! "start"
  }
}
