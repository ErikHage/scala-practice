//foldLeft and foldRight

//binary operations on all the elements of a List

//class List[+A] {
//  def foldLeft[B](z:B)(f: (B,A) => B) : B
//  def foldRight[B](z:B)(f: (A,B) => B) : B
//}

//can be used to simplify map and flatten

def map[A,B](xs:List[A])(f: A=>B): List[B] = {
  val startValue = List.empty[B]
  xs.foldRight(startValue) { f(_) :: _ }
}

def flatten[B](xss:List[List[B]]): List[B] = {
  val startValue = List.empty[B]
  xss.foldRight(startValue) { _ ::: _ }
}

//foldRight is recursive and can throw stack overflow errors
//use foldLeft instead:

def map2[A,B](xs:List[A])(f: A=>B): List[B] = {
  val startValue = List.empty[B]
  xs.foldLeft(startValue)((a,x) => f(x) :: a).reverse
}

//use foldLeft to calculate the sum and length of a List
//sum (could also use .sum)
List(1,2,3,4).foldLeft(0) { _ + _ }
//length
List(1,2,3,4).foldLeft(0) { (a,b) => a + 1 }

//symbolic version exist, but not often used
// foldLeft  = /:
// foldRight = :\

//use foldLeft to determine if elements exist in a list
def exists[A](xs:List[A], e:A): Unit = {
  xs.foldLeft(false)((a,x) => a || (x == e))
}

