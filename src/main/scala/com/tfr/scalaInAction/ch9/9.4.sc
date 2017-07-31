import java.io.File

import scala.concurrent.{Future, Promise}
//Composing concurrent programs with Future and Promise

//a Future is an object that can hold a value that may become available at a later time
//proxy to an actual value that does not yet exist

//def someFuture[T]: Future[T] = Future {
//  someComputation()
//}

//Need ExecutionContext
//import scala.concurrent.ExecutionContext.Implicits.global

//when a Future has the value it is considered completed. A Future could also be completed with an Exception.
//use th onComplete callback method
//someFuture.onComplete {
//  case Success(result) => println(result)
//  case Failure(t) => t.printStackTrace
//}

//Futures can also be created using Promise
//  Promise can be considered as a writable, single assignment container
//  You can use Promise to create a Future, which will be completed when Promise is fulfilled with a value

val promise: Promise[String] = Promise[String]()
val future = promise.future

val anotherFurture = Future {
  promise.success("Done")
  //doSomethingElse()
}

future.onSuccess{
  case message =>
    println(message)
    //startTheNextStep()
}

//-----------Divide and Conquer with Future
//word count problem using Future

//four steps
// 1.Scan for all the files in a given directory
// 2.count words in a given file
// 3.accumulate and sort the result
// 4.produce the result

//example in example.WordCountWithFuture


//----------Mixing Future with Actors

//two common patterns
//1.send a message to an actor and receive a response from it
//2.reply to a sender when some concurrent task (Future) completes (AKA pipe pattern)

import akka.pattern.{ask, pipe}
import akka.actor._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

implicit val timeout = Timeout(5 seconds)

class GreetingActor2 extends Actor {
  val messageActor = context.actorOf(Props[GreetingsChildActor])

  override def receive = {
    case name =>
      val f: Future[String] = (messageActor ask name).mapTo[String]
      f pipeTo sender
  }

}

class GreetingsChildActor extends Actor {
  override def receive = {
    case _ => ""
  }
}


