//Functional programming

import scala.io.Source

//pure function = for f(x) = y each value of x maps to exactly one value of y
//NO SIDE EFFECTS
//def f:X => Y

//for the given x you will always get the same y
def add(a:Int, b:Int): Int = a + b
add(10,10)

//example of not pure function:
//weather changes based on when you invoke the function
def weather(zipCode:String) = {
  val url = "http://api.wunderground.com/auto/wui/geo/GeoLookupXML/index.xml?query="
  Source.fromURL(url + zipCode)
}
//weather("11102")
//referential transparency
//  An expression could be replaced by its value without affecting the program
//example
//  you can replace the add call with the value and get the same outcome
add(10,10) + add(5,5)
20 + add(5,5)
add(10,10) + 10
20 + 10


//------------------Benefits of referential transparency

//provides the ability to reason about your code
//  you can provide proof that your program works correctly by replacing pure functions with their values and reducing a complex expression to a simpler one

//method vs function
// method: don't have any type, only associated with the enclosing class
// function: represented by a type and object


//-------------A pure functional program

object PureFunctionalProgram {
  def main(args: Array[String]): Unit = {
    println(singleExpression(args.toList))
  }
  def singleExpression: List[String] => (List[Int], List[Int]) = {
    a => a map (_.toInt) partition(_<30)
  }
}

val args = new Array[String](3)
args(0) = "25"
args(1) = "35"
args(2) = "45"
PureFunctionalProgram.main(args)