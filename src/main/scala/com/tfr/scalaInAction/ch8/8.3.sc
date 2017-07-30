import scala.xml.Elem
//Ad hoc polymorphism with type classes

//type classes are a way to define commonalities among sets of types


//---------Modeling orthogonal concerns using type classes
case class Movie(name:String, year:Int, rating:Double)

//could add a toXml method to the case class (dirty solution)
//but that's inappropriate because it shouldn't be the responsibility of that class

//could use object Adaptor pattern
trait XmlConverter[A] {
  def toXml(a:A): String
}
object MovieXmlConverter extends XmlConverter[Movie] {
  def toXml(a: Movie): Elem = {
    <movie>
      <name>{a.name}</name>
      <year>{a.year}</year>
      <rating>{a.rating}</rating>
    </movie>
  }
}
val m = Movie("Inception", 2010, 10)
val xml = MovieXmlConverter.toXml(m)
//this also isn't great because it hides the movie object, and the rigidity of design

//Type class
//first define a concept - we'll use the XmlConverter trait from above
//second - propagate constraints automatically to a generic algorithm
//   for example, you could create a new method that takes an instance of a type and a converter to convert to xml (but that's much of an improvement)
//type classes are practical because of the implicit keyword

def toXml[A](a: A)(implicit converter: XmlConverter[A]) = converter.toXml(a)

//now you can invoke toXml by passing an instance of movie and the compiler will automatically provide the converter for you

//complete example in example.XmlConverter

//for an example in scala see the sum method for List
//def sum[B :> A](implicit num: Numeric[B]): B
//the numeric trait is nothing but a type class

val l = List(1,2,3)
l.sum

val ll = List("a","b","c")
//ll.sum //compilation error bc no implicit value for parameter num

//same behavior for min
//def min[B >: A](implicit cmp: Ordering[B]): A

//new syntax for implicit parameters in Scala 2.8
//def toXml[A: XmlConverter](a: A) = implicitly[XmlConverter[A]].toXml(a)
//using A: XmlConverter, you're declaring that the toXml method takes an implicit parameter of type XmlConverter[A]
//bc the parameter name isn't available, you can use the implicitly method defined by scala.Predef to get reference to the implicit parameter


//----------Solving the expression problem with type classes

//see example.expressionTypeClass



