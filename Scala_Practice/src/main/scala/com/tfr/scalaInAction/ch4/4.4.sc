import scala.collection.GenTraversableOnce
//Higher-order functions, including map, flatMap, and friends

//a function is called higher order if it takes a function as an argument or returns a function as a result

//the map function is higher order
//class List[+A] ... {
//  def map[B](f: A => B) : List[B]
//}

List(1,2,3) map { (x:Int) => x + 1 }
List(1,2,3) map { _ + 1 }
def addOne(num:Int) = num + 1
List(1,2,3) map addOne

//returns a function
def addOne2(num:Int) = {
  def ++ = (x:Int) => x + 1
  ++(num)
}
List(1,2,3) map addOne2

//implement map recursively
def map[A,B](xs: List[A], f: A=>B): List[B] = {
  xs match {
    case List() => scala.collection.immutable.Nil
    case head :: tail => f(head) :: map(tail, f)
  }
}

//implement map with for-comprehension
def map1[A,B](f:A=>B, xs:List[A]) : List[B] = for(x <- xs) yield f(x)


//flatMap - flattens collection of collections into a single list
//class List[+A] {
//  def flatMap[B](f: A=>GenTraversableOnce[B]) : List[B]
//}

List("one","two","three","").flatMap(_.toList)

def flatten[B](xss: List[List[B]]) : List[B] = {
  xss match {
    case List() => scala.collection.immutable.Nil
    case head :: tail => head ::: flatten(tail)
  }
}
//also an example of currying (allows you to chain functions with a single parameter)
def flatMap[A,B](xs:List[A])(f: A=>List[B]) : List[B] = {
  flatten(map(xs, f))
}
//currying allows you to pass functions as closures
flatMap(List("one","two","three")) { _.toList }

//implemented using tail recursion (to avoid stack overflow errors)
def flatten3[B](xss:List[List[B]]): List[B] = {
  def _flatten3(oldList: List[List[B]], newList: List[B]) : List[B] = {
    oldList match {
      case List() => scala.collection.immutable.Nil
      case head :: tail => _flatten3(tail, newList ::: head)
    }
  }
  _flatten3(xss, Nil)
}


//call by name
val logEnabled = true
//normal
def log(m:String) = if(logEnabled) println(m)
//call by name
def log2(m: => String) = if(logEnabled) println(m)

