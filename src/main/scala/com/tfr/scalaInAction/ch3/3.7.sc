//case classes

case class Person(firstName:String, lastName:String)

val person = new Person("Erik","Hage")
val person2 = new Person("Erik","Hage")
val person3 = new Person("Erik","Hage2")

//all params are public vals (unless var specified)
person.firstName
person.lastName

//equals and hashCode implemented on given params
person.eq(person2)
person.eq(person3)
person.hashCode()
person2.hashCode()
person3.hashCode()

//toString
person.toString

//copy method
val copy = person.copy()

//companion obj created with appropriate apply method

//compiler adds method unapply, which allows the class name to be used as an extractor for pattern matching

//default implementation provided for serialization

//can make a case object also

person match {
  case Person(first, last) => println(">>>> " + first + ", " + last)
}

val people = List(
  Person("Erik", "Hage"),
  Person("Erik", "Hage2"),
  Person("Erik", "Hage3")
)

for(Person(first,last) <- people) yield first + "," + last

/*
hard coded companion object for person would look like this:

object Person {
  def apply(firstName:String, lastName:String) = {
    new Person(firstName, lastName)
  }
  def unapply(p:Person): Option[(String,String)] = {
    Some((p.firstName, p.lastName))
  }
}
 */