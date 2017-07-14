//var - mutable instance variables
//val - immutable values
//without either - private values

class Address(
  val id:String,
  private val address1:String,
  val address2:String,
  var city:String,
  var zipCode:Int,
  code: String,
  private var _someValue:String
) {

  //getter and setter
  def someValue = _someValue
  def someValue_=(newValue:String) = _someValue = newValue
}

var a = new Address("1","230 Quail Lane South","","New York", 11102, "code", "someValue")

//can change zipCode
a.zipCode = 12345

//can access, but not change id
a.id
//a.id = "2"

//can't access code
//a.code

//can't access address1
//a.address1

//SCRIPT within a constructor
//scala puts any inline code defined inside the class into the primary constructor
class MyScript(host:String) {
  require(host != null, "Have to provide a host name")
  if(host == "127.0.0.1") println("host = localhost")
  else println("host = " + host)
}

val s1 = new MyScript("127.0.0.1")
val s2 = new MyScript("127.0.0.3")
val s3 = new MyScript(null)