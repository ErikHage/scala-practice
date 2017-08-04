import java.io.FileWriter

import akka.actor.ActorSystem

import scala.collection.immutable.HashMap
import scala.concurrent.stm._
//Simple concurrency with Akka

//use concurrency to SCALE UP

//techniques available in Akka:
// 1. Actors : objects that process messages asynchronously and encapsulate state. message-passing concurrency
// 2. Software Transactional Memory (STM) : a concurrency model analogous to database transactions for controlling access to a shared state. It’s a better alternative to locks and provides composability
// 3. Agents : Agents provide abstraction over mutable data. They only allow you to mutate the data through an asynchronous write action.
// 4. Dataflow : is deterministic. This means that it behaves the same every time you execute it. So if your problem deadlocks the first time, it will always deadlock, helping you to debug the problem. Akka implements Oz-style[a] dataflow concurrency using Future.

//can be combined!


//-------Remote Actors

//actors can communicate across multiple JVMs
//messages serialized with google protocol buffer : like xml but smaller and faster
//communication though JBoss Netty : non blocking I/O (to be replaced by an actor-based I/O library called Actor I/O)

//transparent remoting: remoteness of the actor is completely configured at deployment time (can use local for dev and remote during deployment)

//dependencies for remote servers

//resolvers ++= Seq(
//  "Akka Repo" at "http://akka.io/repository",
//  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/repo"
//)
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.1.0",
//  "com.typesafe.akka" %% "akka-remote % "2.1.0"
//)

//configuration for remote actors: application.conf in src/main/resources
//separated by actor systems

//Change the actor provider to akka.remote.RemoteActorRefProvider.
//  Add the host name of the machine in which the actor system will be running. Make sure this IP address is reachable.
//  The port number which the remote actor system should listen on.
//  Map the actor name to the actor system in which it will be deployed


//------Making mutable data safe with STM

//turns java heap into a transactional dataset
// Atomicity :  This property states that all modifications should follow the “all or nothing” rule.
// Consistency : ensures the transaction takes the system from onr consistent state to another
// Isolation : no other transaction can see partial transaction

//free from locks
//rolls back from exceptions and is composable
//can combine two smaller operations into a bigger one

val ref1 = Ref(HashMap[String, Any](
  "service1" -> "10",
  "service2" -> "20",
  "service3" -> null
))
val ref2 = Ref(HashMap[String, Any]())

//Refs are mutable references to values that you can share safely with multiple concurrent participants
//to perform operations on the Refs:

def atomicInsert(key: String, value: Int) = atomic { implicit txn =>
  val oldMap = ref2.get
  val newMap = oldMap + ( key -> value )
  ref2.swap(newMap)
}

def atomicDelete(key: String): Option[Any] = atomic { implicit txn =>
  val oldMap = ref1.get
  val value = oldMap.get(key)
  ref1.transform(_ - key)
  value
}

def atomicSwap(key: String) = atomic { implicit txn =>
  val value: Option[Any] = atomicDelete(key)
  atomicInsert(key, Integer.parseInt(value.get.toString))
}

//TESTING
//"Atomic operations in composition" should {
//  "rollback on exception" in {
//    swap("service3")
//    ref1.single().contains("service3") must beEqualTo(true)
//    ref2.single().contains("service3") must beEqualTo(false)
//  }
//}


//---------Agents

//asynchronous changes to any individual storage location bound to it
//only mutate the location by applying an action (functions asynchronously applied to the state of Agent and in which the return value becomes the new value of Agent)
//reading a value is synchronous and instantaneous

import akka.agent.Agent
implicit val system = ActorSystem("agentExample")
val writer = new FileWriter("src/test/resources/log.txt")
val a = Agent(writer)
a.send(w => {
  w.write("This is a log message")
  w
})
a.close()
writer.close()

//Agent will run until the close method is called


//-------Dataflow

//deterministic concurrency model
//  if you run it and it works, it will always work without deadlock
//  sequential code that performs parallel operations
//  limitation: code should be completely side-effect free

//implemented using scala's delimited continuous compiler plug-in

//autoCompilerPlugins := true
//
//libraryDependencies <+= scalaVersion { v => compilerPlugin(
//  "org.scala-lang.plugins" % "continuations" % v) }
//
//scalacOptions += "-P:continuations:enable"
//
//libraryDependencies += "com.typesafe.akka."  %% " akka-dataflow"  %  "2.1.0" //not working??

//you must work with dataflow variables
//  like a single-assignment variable
//  once bound it won't change, and subsequent attempts to bind a value will be ignored

//val messageFromFuture = Promise[String]()

//anydataflow operation is performed in the Future.flow block:

//Future.flow {
//  messageFromFuture()
//}

//preceding call will wait in a thread unless a value is bound to messageFromFuture
//Future.flow returns a Future so can perform other operations without blocking the main thread of execution
//Future is a data structure to retrieve the result of some concurrent operation
//to assign a value to a dataflow variable:

//Future.flow {
//  messageFromFuture << "Future looks very cool"
//}

//once a value is bound, all the Futures that are waiting on the value will be unblocked and able to continue with execution.

//import akka.actor._
//import akka.dispatch._
//import Future.flow
//
//object Main extends App {
//  implicit val system = ActorSystem("dataflow")
//  val messageFromFuture, rawMessage, parsedMessage = Promise[String]()
//  flow {
//    messageFromFuture << parsedMessage()
//    println("z = " + messageFromFuture)
//  }
//  flow {
//    rawMessage << "olleh"
//  }
//  flow {
//    parsedMessage << toPresentFormat(rawMessage())
//  }
//
//  def toPresentFormat(s: String) = s.reverse
//}