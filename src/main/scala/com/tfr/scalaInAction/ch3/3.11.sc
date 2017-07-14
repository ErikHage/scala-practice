//Implicit conversion with implicit classes

//implicit conversion = method that takes one type of parameter and return antoher type

//val someInt:Int = 2.3 //compilation error

//explicit
def double2Int(d:Double):Int = d.toInt
val someInt:Int = double2Int(2.3)

//implicit
implicit def doubleToInt(d:Double): Int = d.toInt
val someInt2:Int = 2.3


//common usage - add extension methods to existing types
val onTo10 = 1 to 10

class RangeMaker(left:Int) {
  def -->(right:Int) = left to right
}

val range:Range = new RangeMaker(1).-->(10)


//implicit def int2RangeMaker(left:Int): RangeMaker = new RangeMaker(left)
//val newRange = 1-->10

//can combin the RangeMaker and conversion methods by making the class implicit

//avoid runtime cost by turning into a value class
implicit class RangeMaker2(val left:Int) extends AnyVal {
  def --> (right:Int):Range = left to right
}
1-->10