//for-comprehension
//imperative form

val files = new java.io.File("D:\\Git\\ScalaPractice\\src\\main\\scala\\scalaInAction\\ch2").listFiles()

for(file <- files) {
  val filename = file.getName
  if(filename.endsWith(".scala")) println(file)
}

for(
  file <- files;
  fileName = file.getName
  if fileName.endsWith(".scala")
) println(file)

//multiple generators
//like nested loops

val aList = List(1,2,3)
val bList = List(4,5,6)

for { a <- aList; b <- bList }
  println(a+b)

//functional form

val result = for { a <- aList; b <- bList } yield a + b

for(r <- result) print(r)

//val xmlNode = <result>{result.mkString(",")}</result>

for { a <- aList; b <- bList } yield { print(a+b) }