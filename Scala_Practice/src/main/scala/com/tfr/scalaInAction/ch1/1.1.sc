val list = List(1,2,3,4,5)
val list2 = list.filter(n => n>3).map(n => n+4)
val list3 = list.map(n => n+4)

class UAPExample {
  val someField = "hi"
  def someMethod = "there"
}

val o = new UAPExample
o.someField
o.someMethod