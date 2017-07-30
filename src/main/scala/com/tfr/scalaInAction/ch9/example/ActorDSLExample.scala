package com.tfr.scalaInAction.ch9.example

/**
  * Created by Erik on 7/30/2017.
  */
object ActorDSLExample extends App {
  import akka.actor.ActorDSL._
  import akka.actor.ActorSystem

  implicit val system = ActorSystem("actor-dsl")

  val testActor = actor(new Act {
    become {
      case "ping" => sender ! "pong"
    }
  })

  actor(new Act {
    whenStarting { testActor ! "ping"}
    become {
      case x =>
        println(x)
        context.system.shutdown()
    }
  })
}
