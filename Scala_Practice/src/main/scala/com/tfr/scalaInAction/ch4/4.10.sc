import java.net.URL
//Working with lazy collections!

//strict collections > eager evaluation
List(1,2,3,4,5).map(_+1).head
//all elements processed in above

//----------Convert a strict collection to a non-strict collection using view

List(1,2,3,4,5).view.map(_+1).head

//can avoid errors
//def strictProcessing = List(-2,-1,0,1,2) map{2/_} //divide by 0 error

def nonStrictProcessing = List(-2,-1,0,1,2).view map{2/_}
nonStrictProcessing.head
nonStrictProcessing(1)
//nonStrictProcessing(2) //divide by zero error

//to force strict processing use .force
//nonStrictProcessing.force //divide by 0 error

import scala.io._
import scala.xml.XML

def tweets(handle:String) = {
  println("processing tweets for " + handle)
  val source = Source.fromURL(new URL("http://search.twitter.com/search.atom?q=" + handle))
  val iterator = source.getLines()
  val builder = new StringBuilder
  for(line <- iterator) builder.append(line)
  XML.loadString(builder.toString)
}

//this evaluates all tweets immediately
//val allTweets = Map(
//  "nraychaudhuri" -> tweets("nraychaudhuri"),
//  "ManningBooks" -> tweets("ManningBooks"),
//  "bubbl_scala" -> tweets("bubbl_scala"))

//use a partial function (don't specify all parameters):
val allTweets = Map(
  "nraychaudhuri" -> tweets _,
  "ManningBooks" -> tweets _,
  "bubbl_scala" -> tweets _)

//tweets("RedWhiteBrewBC") //gets tweets back
tweets _ //get a function back

//allTweets.view.map{ t => t._2(t._1) }.head //only get tweets for first
//for(t <- allTweets; if t._1 == "ManningBooks") t._2(t._1) //for a specific handle


//-----------Working with streams (lazy lists)

List("zero","one","two","three","four","five").zip(Stream.from(0))

//Fibonacci sequence
//recursively (not efficient)
def fib(n:Int): Int = n match {
  case 0 => 0
  case 1 => 1
  case _ => fib(n-1) + fib(n-2)
}
fib(0)
fib(1)
fib(8)

//use Streams
val fibS: Stream[Int] = Stream.cons(0,
  Stream.cons(1, fibS.zip(fibS.tail).map(t => t._1 + t._2))
)

fibS(1)
fibS(2)
fibS(8)

//cons apply method:
//def apply[A](hd:A, t1: =>Stream[A]) = new Cons(hd, t1)

//here t1 is a call-by-name parameter, which is encoded by turning it into a no-arg function
//when pattern matching a stream, the usual cons doesn't work (::), you need to use #::
