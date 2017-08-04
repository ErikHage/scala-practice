//Functions
//---------Methods vs. Functions
import com.tfr.scalaInAction.ch5.examples.Pure._

import scalaz.Cofree_

//common form of a function is the member of a class, a method
class UseResource {
  def use(r:Resource): Boolean = {
    true
  }
}

//scala transforms functions into objects
val succ = (x:Int) => x+1
//transforms to
val succFunction = new Function1[Int, Int] {
  def apply(x:Int) : Int = x + 1
}

//transform method to functions via Eta-Expansion
val use_function: Resource => Boolean = (new UseResource).use _


//--------------Higher-Order Functions (take functions as arguments)

val l = List(1, 2, 3, 5, 7, 10, 15)
l.filter(_ % 2 == 0) //filter is a higher-order function

//common problem: resource handling: Loan Pattern
//define a common type like Resource to create abstraction
def use[A, B <: Resource ](r: Resource)(f: Resource => A): A = {
  try {
    f(r)
  } finally {
    r.dispose()
  }
}
//use(someSocketResource) { r => sendData(r) }

def tap[A](a: A)(sideEffect: A => Unit): A = {
  sideEffect(a)
  a
}


//----------Function Currying

//transforming a function of many parameters into one of one parameter
//a form of dependency injection

trait TaxStrategy {
  def taxIt(product: String): Double
}

val taxIt: (TaxStrategy, String) => Double = (s, p) => s.taxIt(p)
taxIt.curried
//now takes one parameter, and returns a function that takes the second parameter

class TaxFree extends TaxStrategy {
  override def taxIt(product: String) = 0.0
}
val taxFree = taxIt.curried(new TaxFree)
taxFree("someProduct")

//can also turn existing methods into curried functions using an underscore
def taxItMethod(s: TaxStrategy, product: String) = {
  s.taxIt(product)
}
val taxItF = taxItMethod _
taxItF.curried

//can also define methods in currying style using multiple parameter sets
def taxItM(s: TaxStrategy)(product: String) = {
  s.taxIt(product)
}
val taxFreeM = taxItM(new TaxFree) _


//---------Function composition and Partial Functions

//partial functions: only defined for a subset of input values
//methods: apply, and isDefinedAt(a:A):Boolean

//create a partial function by defining an anonymous function w/ pattern matching
def intToChar: PartialFunction[Int, Char] = {
  case 1 => 'a'
  case 3 => 'c'
}

//translates to:
new PartialFunction[Int, Char] {
  def apply(i: Int) = i match {
    case 1 => 'a'
    case 3 => 'c'
  }

  def isDefinedAt(i: Int): Boolean = i match {
    case 1 => true
    case 3 => true
    case _ => false
  }
}

//can combine with orElse or andThen
//PERFORMANCE penalty: isDefinedAt may get called many times

//example: see Claim.scala

//Use case: Partial functions for exception cases, to avoid throwing Exceptions


//------------Recursion

//can avoid use of mutable temporary variables
//works well with scala pattern matching

//sum a list:
//  imperative
var sum = 0;
for(e <- List(1,2,3)) { sum += e}
sum
//  recursive
def sum(xs:List[Int]): Int = xs match {
  case Nil => 0
  case x :: ys => x + sum(ys)
}
sum(List(1,2,3))