//modifiers
val s =1

//private - only in enclosed class
//package outerpkg.innerpkg
class Outer {
  class Inner {
    private[Outer] def f() = ""
    //private[innerpkg] def g() = ""
    //private[outerpkg] def h() = ""
    private[this] def i() = ""
    private def j() = ""
  }
}

//protected like private modifiers
//class, subclasses, and companion obj

//override mandatory when overriding concrete member of parent
//can be combined with abstract(only for traits)
  //means must be mixed w/ a class that provides implementation

trait DogMood {
  def greet
}

trait AngryMood extends DogMood {
  abstract override def greet = {
    println("bark")
    super.greet
  }
}

//sealed - class definition
//can be overridden only by subclasses in same source file
//common pattern for defined set of subclasses but don't want others to subclass it