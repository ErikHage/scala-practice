package com.tfr.scalaInAction.ch9.example

import akka.actor.{Actor, ActorRef, ActorSystem, AllForOneStrategy, OneForOneStrategy, Props, SupervisorStrategy}
import java.io._

import akka.actor.SupervisorStrategy.Restart

import scala.concurrent.duration._
import scala.io._

/**
  * Created by Erik on 7/30/2017.
  */
case class StartCounting(docRoot:String, numActors:Int)
case class FileToCount(fileName:String)
case class WordCount(fileName:String, count:Int)


class WordCountWorker extends Actor {

  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy(
    maxNrOfRetries = 3,
    withinTimeRange = 5 seconds
  ) {
    case _: Exception => Restart
  }

  def receive: PartialFunction[Any, Unit] = {
    case FileToCount(fileName:String) =>
      val count = countWords(fileName)
      sender ! WordCount(fileName, count)
  }
  def countWords(fileName:String): Int = {
    val dataFile = new File(fileName)
    Source.fromFile(dataFile).getLines.foldRight(0){ _.split(" ").length + _ }
  }
  override def postStop(): Unit = {
    println(s"Worker actor is stopped: $self")
  }
}

class WordCountMaster extends Actor {
  //safe to use mutable state inside an actor bc the actor system will ensure that no two threads will execute an instance of an actor at the same time. You must still make sure you don't leak the state outside the actor
  var fileNames: Seq[String] = Nil
  var sortedCount: Seq[(String, Int)] = Nil

  override val supervisorStrategy: SupervisorStrategy = AllForOneStrategy() {
    case _: Exception =>
      println("Restarting...")
      Restart
  }

  def receive: PartialFunction[Any, Unit] = {

    case StartCounting(docRoot, numActors) =>
      val workers = createWorkers(numActors)
      fileNames = scanFiles(docRoot)
      beginSorting(fileNames, workers)

    case WordCount(fileName, count) =>
      println("File " + fileName + " counted: " + count)
      sortedCount = sortedCount :+ (fileName, count)
      sortedCount = sortedCount.sortWith(_._2 < _._2)
      println("SortedCount now at: " + sortedCount.size)
      if(sortedCount.size == fileNames.size) {
        println("final result " + sortedCount)
        finishSorting()
      }
  }

  override def preStart(): Unit = {
    super.preStart()
    println(s"Master actor is started: $self")
  }
  override def postStop(): Unit = {
    println(s"Master actor is stopped: $self")
  }
  private def createWorkers(numActors:Int) = {
    for(i <- 0 until numActors) yield
      context.actorOf(Props[WordCountWorker], name = s"worker-$i")
  }
  private def scanFiles(docRoot:String) = {
    new File(docRoot).list.map(docRoot + _)
  }
  private[this] def beginSorting(fileNames:Seq[String], workers:Seq[ActorRef]) = {
    fileNames.zipWithIndex.foreach(e => {
      workers(e._2 % workers.size) ! FileToCount(e._1)
    })
  }
  private[this] def finishSorting() {
    context.system.shutdown()
  }
}

object MainActor {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("word-count-system")
    val m = system.actorOf(Props[WordCountMaster], name="master")
    m ! StartCounting("src/main/resources/", 2)
  }
}

