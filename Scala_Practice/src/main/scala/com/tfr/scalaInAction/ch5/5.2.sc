//Moving from OOP to functional programming

//----------Pure vs Impure programming

//OOP/Impure
//  built around mutable state/side effects
//  classes with collections of methods that share and mutate data

//Functional/Pure
//  problems are solved by applying functions to data
//  data represented by value, functions result in new values

class Square(var side:Int) {
  def area = side * side
}
//above not pure because side is mutable, and side is used to calc area

//functional style (Successor Value pattern)
class PureSquare(val side:Int) {
  def newSide(s:Int):PureSquare = new PureSquare(s)
  def area = side * side
}


//--------------Object oriented patterns in functional programing
//design patterns are just as useful, some are already implemented
//  Singleton, Factory, Visitor(pattern-matching)

def calculatePrice(product: String, taxingStrategy: String => Double) = {
  //other stuff
  val tax = taxingStrategy(product)
  //other stuff
}

//higher-order functions are great for dependency injection

trait TaxStrategy {
  def taxIt(product: String): Double
}
class ATaxStrategy extends TaxStrategy {
  def taxIt(product: String): Double = 2.3
}
class BTaxStrategy extends TaxStrategy {
  def taxIt(product: String): Double = 4.5
}

def taxIt: TaxStrategy => String => Double = s => p => s.taxIt(p)

def taxIt_a: String => Double = taxIt(new ATaxStrategy)
def taxIt_b: String => Double = taxIt(new BTaxStrategy)


//------------Modelling purely functional problems

//impure side effects are unavoidable at the enterprise level
//keep them abstracted, keep the rest of the program as pure as possible

//example: HTTP server, see Pure.scala