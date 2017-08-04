//higher-kinded type - a type of parameter type

//not so great implementations
def sumList(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)
def sumArray(xs: Array[Int]): Int = xs.foldLeft(0)(_ + _)

//create an abstraction for all collections as a type
trait Summable[A] {
  def plus(a1:A, a2:A): A
  def init:A
}

//now for each type that supports the + function, implement this trait

object IntSummable extends Summable[Int] {
  def plus(a1:Int, a2:Int): Int = a1 + a2
  def init: Int = 0
}
object StringSummable extends Summable[String] {
  def plus(a1:String, a2:String): String = a1 + a2
  def init: String = ""
}

//now implement the logic to sum all the elements of a collection

trait Foldable[F[_]] {
  def foldLeft[A](xs: F[A], m: Summable[A]): A
}

object ListFoldLeft extends Foldable[List] {
  def foldLeft[A](xs: List[A], m: Summable[A]) = xs.foldLeft(m.init)(m.plus)
}
object ArrayFoldLeft extends Foldable[Array] {
  def foldLeft[A](xs: Array[A], m: Summable[A]) = xs.foldLeft(m.init)(m.plus)
}

//use these traits to implement a generic Sum function
def sum[F[_],A](xs:F[A], f:Foldable[F], m:Summable[A]): A = f.foldLeft(xs, m)

//in use:
sum(List(1,2,3), ListFoldLeft, IntSummable)
sum(Array("one","two","three"), ArrayFoldLeft, StringSummable)

//above is not great in a small context, but in a larger context it is powerful