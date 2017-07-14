object RichConsole {
  def p(x:Any) = println(x)
}

RichConsole.p("test")

import RichConsole._
p("imported test")

//the factory pattern in scala
abstract class Role {
  def canAccess(page:String): Boolean
}

class Root extends Role {
  override def canAccess(page: String) = true
}

class SuperAnalyst extends Role {
  override def canAccess(page: String) = page != "admin"
}

class Analyst extends Role {
  override def canAccess(page: String) = false
}

object Role {
  def apply(roleName: String) = roleName match {
    case "root" => new Root
    case "superAnalyst" => new SuperAnalyst
    case "analyst" => new Analyst
  }
}

val root = Role("root")
val superA = Role("superAnalyst")
val analyst = Role("analyst")