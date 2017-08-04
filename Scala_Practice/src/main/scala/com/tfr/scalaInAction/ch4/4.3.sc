import com.tfr.scalaInAction.ch4.{Just, Maybe, Nil}
//Lower and Upper type bounds

def position[A](xs:List[A], value:A): Maybe[Int] = {
  val index = xs.indexOf(value)
  if(index != -1) Just(index) else Nil
}

val xs = List("one","two","three")

position(xs, "two").get

//throws exception
//position(List(), "two").get


//upper bound T <: A
//type variable T is a subtype of A, and A is the upper bound (only subclasses of A)
def defaultToNull[A <: Maybe[_]](p:A) = {
  p.getOrElse(null)
}

defaultToNull(Just(1))
defaultToNull(Nil)


//lower bound T >: A
//type T is constrained to some super type of A
//example: getOrElse in the Maybe class

position(List(), "something").getOrElse(-1)

position(xs, "three").getOrElse(-1)