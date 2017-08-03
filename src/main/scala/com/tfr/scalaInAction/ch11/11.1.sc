//Using Java classes in Scala

//joda time is a good example of a useful java library

//SBT can build mixed java and scala projects out of the box
//Maven can with a plugin


//------Working with java static members

//scala doesn't have the static keyword, so it interprets them as members of a companion object

import com.tfr.scalaInAction.ch11._ //import java class that uses joda time
import java.util.Date

class PaymentCalculator(val payPerDay: Int = 100) extends DateCalculator {
  def calculatePayment(start: Date, end: Date) = {
    daysBetween(start, end) * payPerDay
  }
  def chronologyUsed = DateCalculator.getChronologyUsed  //how to access a java static member
}

//visibility issues
//scala and java implement visibility differently

//scala enforces visibility at compile time but makes everything public at runtime (because companion objects are allowed to access protected members of companion classes, and that can’t be encoded at the bytecode level without making everything public)

//java enforces visibility rules both at compile time and runtime

//if you have a protected static member defined in a Java class, there’s no way to access that member in Scala


//---------Working with java checked exceptions

//here you don't need a try/catch (In Scala, you can invoke the method without a try/catch block):

//JAVA
//import java.io.*;
//public class Writer {
//  public void writeToFile(String content) throws IOException {
//    File f = File.createTempFile("tmpFile", ".tmp");
//    new FileWriter(f).write(content);
//  }
//}
//SCALA
//def write(content: String) = {
//  val w = new Writer
//  w.writeToFile(content)
//}

//scala compiler won't force you to catch it, don't rethrow exceptions from scala (bad practice) you should create an instance of Either or Option:

//def write(content: String): Either[Exception, Boolean] = {
//  val w = new Writer
//  try {
//    w.writeToFile(content)
//    Right(true)
//  }catch {
//    case e: java.io.IOException => Left(e)
//  }
//}

//now you can compose with the result (remember exceptions don't compose)


//------Working with java generics using existential types

//generics transfer straightforwardly to scala type parameters
//wildcard types not as simple: Vector<?>

//translates to existential type in scala:

val v:Vector[_] forSome {type T}

//when switching between java and scala code, you should convert the collections:
scala.collection.JavaConversions //provides a series of implicit conversions that convert between a Java collection and the closest corresponding Scala collection, and vice versa
scala.collection.JavaConverters //uses a “Pimp my Library” pattern to add the asScala method to Java collection and asJava method to Scala collection types.