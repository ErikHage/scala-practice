//Building your own function objects

object foldl {
  def apply[A,B](xs:Traversable[A], defaultValue: B)(op: (B,A)=>B) = {
    //right associative alternate foldLeft syntax
    (defaultValue /: xs)(op)
  }
}

foldl(List("1","2","3"), "") { _ + _ }
foldl(IndexedSeq("1","2","3"), "") { _ + _ }
foldl(Seq("1","2","3"), "") { _ + _ }

//good idea to extend one of the Function traits
//trait Function1[-T1,+R] extends AnyRef {
//  def apply(v: T1): R
//}

object ++ extends Function1[Int, Int] {
  def apply(p:Int): Int = p + 1
}
val x = ++(2)

////shorthand
//val ++ = (x:Int) => x + 1
//
////alternative syntactic sugar
//object ++ extends (Int => Int) {
//  def apply(p: Int): Int = p + 1
//}

//use with previously defined map function
//map(List(10,20,30), ++)

//same as inline
//map(List(10,20,30), (X:Int) => x + 1)

//same as passing trait
//map(List(10,20,30), new Function1[Int, Int] {
//  def apply(p:Int) = p + 1
//})

//ETA-expansion
//when passing an existing function (not a function object) as a parameter, Scala creates a new anonymous function object with an apply method, which invokes the original function

//compose functions
val addOne: Int => Int = x => x + 1
val addTwo: Int => Int = x => x + 2
val addThree = addOne compose addTwo
//same as - val addThree: Int => Int = x => addOne(addTwo(x))

addThree(1)
