import scala.collection.GenTraversableOnce
//type variance with covariance and contravariance

//covariance(+)
//allows subclasses to override and use narrower types than their superclass in covariant positions such as the return value

//usefulness of Nothing type
//Scala uses scala.Nothing as a return type in situations where the return type isn't clear, such as when an exception is thrown. When you see a method returning Nothing, that means that method won't return successfully

val everything: List[Any] = List(1, 3, "string")

//def ++(that: GenTraversableOnce[A]): Traversable[A]
//Traversable can be Iterable or Seq also

//contravariance(-)
//subtypes go upward

//invarient
//when neither co nor contra variant
//all scala mutable classes are invariant

val xs:List[String] = List("Pants")

val everythings: List[Any] = xs

//changes to Any bc cons :: returns a new List
1 :: xs







