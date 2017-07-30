package com.tfr.scalaInAction.ch9.example

import akka.actor.ActorRef

/**
  * Created by Erik on 7/30/2017.
  */
object GreetingsActor extends App {
  import akka.actor.Actor
  import akka.actor.Props
  import akka.actor.ActorSystem

  case class Name(name:String)

  class GreetingActor extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case Name(n) => println("Hello " + n)
    }
  }

  val system = ActorSystem("greetings")
  val a: ActorRef = system.actorOf(Props[GreetingActor], name = "greetings-actor")

  a ! Name("Erik")

  Thread.sleep(50)
  system.shutdown()
}
