def rangeMatcher(num:Int) = num match {
  case within10 if within10 <= 10 => println("0 to 10")
  case within100 if within100 <= 100 => println("11 to 100")
  case _ => throw new IllegalArgumentException("Only 0 to 100 allowed")
}

try {
  rangeMatcher(1000)
} catch {
  case e:IllegalArgumentException => e.getMessage
}

try {
  rangeMatcher(10)
} catch {
  case e:IllegalArgumentException => e.getMessage
}