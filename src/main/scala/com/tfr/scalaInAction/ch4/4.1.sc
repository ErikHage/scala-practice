import com.tfr.scalaInAction.ch4._

//type parameterization

def position[A](xs:List[A], value:A): Int = {
  xs.indexOf(value)
}

val xs = List(1,2,3,4,5)
position(xs, 3)

def position2[A](xs:List[A], value:A): Maybe[Int] = {
  val index = xs.indexOf(value)
  if(index != -1) Just(index) else Nil
}