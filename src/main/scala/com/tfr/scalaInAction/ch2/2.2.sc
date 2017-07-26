import java.util

42

println("Hello world!")

val myList = new util.ArrayList[String]()
myList.add("string1")
myList.add("string2")

myList

val x:Int = 1
val decimal = 11235

//val ? = scala.Math.Pi

def ?(x:Double) = scala.math.sqrt(x)

?(9)

val multiLine =
  """
    |This is a
    |multi line
    |string
  """.stripMargin

val name = "Erik"
s"My name is $name"

val height = 1.9d
println(f"$name%s is $height%2.2f meters tall")

val book = <book>
    <title>Scala in Action</title>
    <author>Some guy</author>
  </book>

val message = "xml is fun"
val code = "1"

val alert =
  <alert>
    <message priority={code}>{message}</message>
    <date>{new java.util.Date}</date>
  </alert>

//var placeHolder:String = _

//lazy val forLater = someTimeConsumingOperation()

var a = 1
lazy val b = a+1
a=5
b

val first :: rest = List(1,2,3)

def myFirstMethod() = "exciting times ahead!"

myFirstMethod()

def max(a:Int, b:Int) = if(a>b) a else b
max(5,4)
max(5,7)

def toList[A](value:A) = List(value)
toList(1)
toList("Erik")

val evenNumbers = List(2,4,6,8,10)

evenNumbers.foldLeft(0) { (a:Int,b:Int) => a + b }
evenNumbers.foldLeft(0) { (a,b) => a+b }
evenNumbers.foldLeft(0) { _ + _ }

val hasUpperCase = name.exists(_.isUpper)

val breakException = new RuntimeException("break exception")

def breakable(op: => Unit): Unit = {
  try {
    op
  } catch {
    case _:Throwable =>
  }
}

def break = throw breakException

def install = {
  val env = System.getenv("SCALA_HOME")
  if(env == null) break
  println("found scala home")
}

install




