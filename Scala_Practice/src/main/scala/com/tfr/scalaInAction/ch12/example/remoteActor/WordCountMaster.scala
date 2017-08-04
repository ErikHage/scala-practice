package com.tfr.scalaInAction.ch12.example.remoteActor

import akka.actor._
import com.tfr.scalaInAction.ch9.example.WordCountWorker

/**
  * Created by Erik on 8/3/2017.
  */
class WordCountMaster extends Actor {

  private[this] var urlCount: Int = _
  private[this] var sortedCount : Seq[(String, Int)] = Nil

  def receive: PartialFunction[Any, Unit] = {
    case StartCounting(urls, numActors) =>
      val workers = createWorkers(numActors)
      urlCount = urls.size
      beginSorting(urls, workers)

    case WordCount(url, count) =>
      println(s" $url -> $count")
      sortedCount = sortedCount :+ (url, count)
      sortedCount = sortedCount.sortWith(_._2 < _._2)
      if(sortedCount.size == urlCount) {
        println("final result " + sortedCount)
        finishSorting()
      }
  }

  override def postStop(): Unit = {
    println(s"Master actor is stopped: $self")
  }

  private def createWorkers(numActors: Int) = {
    for (i <- 0 until numActors) yield context.actorOf(Props[WordCountWorker], name = s"worker-$i")
  }

  private[this] def beginSorting(fileNames: Seq[String], workers: Seq[ActorRef]) {
    fileNames.zipWithIndex.foreach( e => {
      workers(e._2 % workers.size) ! FileToCount(e._1)
    })
  }

  private[this] def finishSorting() {
    context.system.shutdown()
  }
}
