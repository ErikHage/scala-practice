//Thinking recursively

//remove duplicates from a list

def removeDupes[A](xs:List[A]): List[A] = xs match {
  case Nil => Nil
  case x :: ys if ys contains x => removeDupes(ys)
  case x :: ys => removeDupes(ys) :+ x
}
removeDupes(List(1,2,3,4,4,4,5,7,8,7,8))

//----------Head Recursion
//above example
//perform the recursive call first and then take the return value from the recursive function and calculate the result

def length[A](xs:List[A]): Int = xs match {
  case Nil => 0
  case x :: ys => 1 + length(ys)
}
length(List(1,2,3,4,5,6,7,8,9))

//----------Tail recursion
//perform calculation first and then execute the recursive call by passing the result of the current step to the next one

//scala overcomes StackOverflowExceptions common to recursion by tail call optimization
import scala.annotation.tailrec

def length2[A](xs:List[A]): Int = {
  @tailrec
  def _length(xs:List[A], currentLength:Int): Int = xs match {
    case Nil => currentLength
    case x :: ys => _length(ys, currentLength)
  }
  _length(xs, 0)
}
length2(List(1,2,3,4,5,6,7,8,9))

//use @tailrec annotation to have compiler tell you if it can optimize your tail recursive functions or methods (as above)

//local function like _length is a common pattern to implement tail recursion