println()
//Building Components
//---------Abstract Type Members

trait Calculator_ {
  type S //abstract type member, identity unknown at declaration
  //specified during the concrete implementation of the enclosing class
}

class SomeCalculator_ extends Calculator_ {
  //has to provide a type for an S type member
  type S = String
}

//BENEFIT - can hide the internal information of a component

//example - price calculator

//common steps across calculators
// 1. connect to a data source (could be of many types)
// 2. calculate the price using the data source
// 3. close the connection to the data source

//template method pattern - fairly successful way to encode common steps and program skeletons (lets you follow a common algorithm across multiple subclasses)

//trait Calculator {
//  def initialize: DbConnection
//  def close(s: DbConnection): Unit
//  def calculate(productId:String): Double = {
//    val s = initialize
//    val price = calculate(s, productId)
//    close(s)
//    price
//  }
//  def calculate(s: DbConnection, productId: String): Double
//}

//the above uses a hardcoded DbConnection, and a calculator that uses a different kind of data source won't be able to use this

//can be fixed by using an abstract type member

trait Calculator {
  type S
  def initialize: S
  def close(s: S): Unit
  def calculate(productId:String): Double = {
    val s = initialize
    val price = calculate(s, productId)
    close(s)
    price
  }
  def calculate(s: S, productId: String): Double
}

//an example using the above

class CostPlusCalculator extends Calculator {
  type S = MongoClient
  def initialize = new MongoClient
  def close(dao: MongoClient): Unit = dao.close()
  def calculate(source: MongoClient, productId: String): Double = {
    //do stuff
    2.3
  }
}
class MongoClient {
  def close(): Unit = println()
}

//abstract type member concept is particularly useful to model a family of types that varies covariantly


//-----------Self Type Members

//allows you to access members of a mixin trait or class, and the Scala compiler ensures that all the dependencies are correctly wired before you're allowed to instantiate the class

trait B {
  def b:Unit = println
}
trait A { self: B => //self type annotation
  def a:Unit = b
}

//trait A can't be mixed in with a concrete class unless that class also extends
//you can access members of B as though they are defined in A
//self is just a name, could be anything (most commonly this/self)

//example - see example.ProductFinder.scala


//------------Building a scalable component

//build an ordering system
//has three components: Order, Inventory, Shipping
//code: see example.OrderingSystem.scala

//all you need to use the example:

import com.tfr.scalaInAction.ch8.example.BookOrderingSystem._
BookOrdering.placeOrder(new BookOrder)


//----------Building an Extensible Component
//solving the "expression problem"
//expression problem:
//  To define a data type with cases, and in which one can add new cases of the type, and operations for the types, without recompiling and maintaining static type-safety

//The Expression Problem and the Extensibility Challenge
//The goal is to define a data type and operations on that data type in which one can add new data types and operations without recompiling existing code, but while retaining static type safety

//satisfy the following requirements:
// extensibility in both dimensions. You should be able to add new types and operations that work on all the types
// strong static type safety. Type casting and reflection are out of the question
// no modification of the existing code and no duplication
// separate compilation

case class Employee(name: String, id: Long)

trait Payroll {
  def processEmployees(employees: Vector[Employee]): Either[String, Throwable]
}
class USPayroll extends Payroll {
  override def processEmployees(employees: Vector[Employee]) = Left("USA")
}
class CanadaPayroll extends Payroll {
  override def processEmployees(employees: Vector[Employee]) = Left("Canada")
}

//adding a new country is easy, just extend Payroll
class JapanPayroll extends Payroll {
  override def processEmployees(employees: Vector[Employee]) = Left("Japan")
}
//this is one type of extension the expression problem talks about. the solution is type-safe, and you can add JapanPayroll as an extension and plug it into an existing Payroll system with a separate compilation

//when you try to add a new operation?
case class Contractor(name:String)

//trait Payroll extends super.Payroll {
//  def processEmployees...
//  def processContractors
//}

//will force you to rebuild everything -> against constraints of problem

//to actually solve it, see example in example.expression package