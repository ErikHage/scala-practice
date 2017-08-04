//import entire package  ._ like java's .*

//inline import
val randomValue = {
  import scala.util.Random
  new Random nextInt
}

//importing class members
import java.lang.System._
nanoTime()

//renaming via import
import java.sql.{Date => SqlDate}
import java.util.Date

val now = new Date()
println(now)
val sqlDate = new SqlDate(now.getTime)
println(sqlDate)


//hide a class using import
import java.sql.{Date => _}
