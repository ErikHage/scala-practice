package com.tfr.akkaoogle.infrastructure

import java.util.Date

import akka.actor.Actor
import akka.agent.Agent
import com.tfr.akkaoogle.calculators.Messages.{FindStats, LogTimeout, Stats}
import com.tfr.akkaoogle.models.TransactionFailure

/**
  * Created by Erik on 8/5/2017.
  */
class MonitorActor extends Actor {
  import context._

  val errorLogger = Agent(Map.empty[String, Int])

  def preRestart(): Unit = errorLogger send {
    old => Map.empty[String, Int]
  }

  override def receive: PartialFunction[Any, Unit] = {
    case LogTimeout(actorId, msg) =>
      logTimeout(actorId, msg)
    case FindStats(actorId) =>
      val timeouts = errorLogger().getOrElse(actorId, 0)
      sender ! Stats(actorId, timeouts = timeouts)
  }

  private def logTimeout(actorId: String, msg: String): Unit = {
    errorLogger send { errorLog =>
      val current = errorLog.getOrElse(actorId, 0)
      val newErrorLog = errorLog + (actorId -> (current+1))
      val l = new TransactionFailure(actorId, msg, new Date(System.currentTimeMillis()))
      l.save
      newErrorLog
    }
  }

}
