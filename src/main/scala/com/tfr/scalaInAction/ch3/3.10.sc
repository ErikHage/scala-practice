//value classes

//rules:
// exactly one val parameter
// param type may not be value class
// no aux constructors
// only def members
// cannot extend any traits, only universal traits

class Wrapper(val name:String) extends AnyVal {
  def up() = name toUpperCase
}

val w = new Wrapper("hey")
w.up()
w

//universal traits - extend Any (normally traits extend AnyRef)
//can only have def members and no initialization code

trait Printable extends Any {
  def p() = println(this)
}
case class Wrapper2(val name:String) extends AnyVal with Printable {
  def up() = name toUpperCase()
}

val w2 = Wrapper2("hey")
w2