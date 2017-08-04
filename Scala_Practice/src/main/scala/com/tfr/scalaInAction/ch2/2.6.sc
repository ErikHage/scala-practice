def ordinal(n:Int) = n match {
  case 1 => println("1st")
  case 2 => println("2nd")
  case 3 => println("3rd")
  case 4 => println("4th")
  case 5 => println("5th")
  case 6 => println("6th")
  case 7 => println("7th")
  case 8 => println("8th")
  case 9 => println("9th")
  case 10 => println("10th")
  case _ => print("Cannot do beyond 10")
}

ordinal(2)
ordinal(6)

def printType(obj:AnyRef) = obj match {
  case s:String => println("String")
  case l:List[_] => println("List")
  case a:Array[_] => println("array")
  case d:java.util.Date => println("date")
  case _ => println("not configured")
}

printType("Hello")
printType(List(1,2,3))
printType(new Array[String](20))
printType(new java.util.Date())

List(1,2,3,4) match {
  case f :: s :: rest => List(f,s)
  case _ => Nil
}

def rangeMatcher(num:Int) = num match {
  case within10 if within10 <= 10 => println("0 to 10")
  case within100 if within100 <= 100 => println("11 to 100")
  case beyond100 if beyond100 < Integer.MAX_VALUE => println("over 100")
}

rangeMatcher(5)
rangeMatcher(50)
rangeMatcher(500)

val suffixes = List("th","st","nd","rd","th","th","th","th","th","th")

def ordinal2(num:Int) = num match {
  case tenTo20 if 10 to 20 contains tenTo20 => num + "th"
  case rest => rest + suffixes(num % 10)
}

ordinal2(1)
ordinal2(11)
ordinal2(21)
ordinal2(36)
