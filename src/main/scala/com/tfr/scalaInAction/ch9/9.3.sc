import akka.actor.{Actor, ActorContext, ActorPath, ActorRef, ActorSystem, Props, SupervisorStrategy}
import com.tfr.scalaInAction.ch9.example.GreetingsActor.GreetingActor
//Implementing message-passing concurrency with actors

//actors communicate with each other through sending and receiving messages
//an actor processes incoming messages and executes the actions associated with it
//typically these messages are immutable because you shouldn't share state between them

//there are two main communication abstractions in actor: send and recieve
//to send a message to an actor: a ! msg
//asynchronous operation
//to receive:
//receive {
//  case pattern1 =>
//  case pattern2 =>
//      ...
//  case patternN =>
//}

//see example in example.GreetingsActor

//-----------What is ActorSystem
//a hierarchical group of actors that share a common configuration
//entry point for creating and looking up actors
//parent actors ("Supervisors") spawn child actors to delegate work until it is small enough to be handled by an individual actor

//a heavy structure that will allocate 1..N threads, so create one per logical subsystem of your application
//ex: one system to handle the backend database, another to handle web service calls, and so forth
//actors are very cheap (~300 bytes) so you can easily create millions of them

//simplest way to create an actor:
val system = ActorSystem(name = "word-count")
val m: ActorRef = system.actorOf(Props[GreetingActor], name = "someActor")

//actor path - like a file system path, a unique identifier

class WordCountWorker extends Actor {
  override def receive: Receive = {
    case s => println(s)
  }
}
val system2 = ActorSystem(name = "word-count2")
system2.actorOf(Props[WordCountWorker], name = "wordCountWorker")
val path: ActorPath = system2 / "WordCountWorker"
val actorRef: ActorRef = system.actorFor(path)
actorRef ! "Some Message"


//--------How do scala Actors work

//every actor system comes with a default MessageDispatcher component
//responsible for sending messages
//backed by a thread pool that is easily configured using the config file (ch12)

//handling a message
// 1.when an actor receives a message in its mailbox, MessageDispatcher schedules the actor for execution. sending and handling messages happen in two different threads. if a free thread is available in the pool that thread is selected for execution of the actor. if all threads are busy, the actor will be executed when threads become available
// 2.The available thread reads the message from the mailbox
// 3.the receive method of the actor is invoked by passing one message at a time

//a single thread always executes a given actor (not the same thread all the time but always just one)


//-----------Divide and Conquer using actors

//problem: count the number of words in each file in a given directory a sort them in ascending order

//implement divide and conquer (fork-join) pattern with actors
//see example.WordCountWorker

//try not to have blocking calls in actors - you are also blocking a thread
//if no other option: separate blocking and nonblocking actors by assigning different messaging dispatchers. you can then assign additional threads, throughput, etc. to the blocking dispatcher





//actor API
//Actor trait only defines one abstract method - receive
// other important methods:
def unhandled(message: Any): Unit //handles messages that don't match in receive
val self: ActorRef //reference to itself
//final def sender: ActorRef //actorRef that sent the last message
val context: ActorContext //information about actor; current message, factory methods to create child actors, access to actor system, lifecycle hooks
def supervisorStrategy: SupervisorStrategy //what will happen when failure is detected in an actor. you can override
def preStart() //called when actor is started ofr the first time, before any message handled
def preRestart() //runs before any restart
def postStop() //after the actor instance is stopped
def postRestart() //runs before any messages processed after a restart

//ActorDSL
//to help create one-off workers or even try in the REPL
import akka.actor.ActorDSL._

//create a simple actor
val testActor = actor(new Act {
  become {
    case "ping" => sender ! "pong"
  }
})

//ZIP
List("a","b","c").zipWithIndex
//result = List((a,0), (b,1), (c,2))


//---------Fault Tolerance made easy with a supervisor