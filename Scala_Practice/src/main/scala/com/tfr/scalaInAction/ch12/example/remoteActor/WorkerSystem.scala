package com.tfr.scalaInAction.ch12.example.remoteActor

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
  * Created by Erik on 8/3/2017.
  */
object WorkerSystem extends App {

  val workerSystem = ActorSystem("workerSystem",
    ConfigFactory.load.getConfig("workerSystem"))

  println("Started the workersystem")

}
