import com.mongodb.BasicDBObject

//named arguments
case class Person2(firstName:String, lastName:String)

val p = Person2("Erik", "Hage")
val p2 = Person2(lastName = "Hage", firstName = "Erik")


trait Person {
  def grade(years:Int): String
}

class SalesPerson extends Person {
  def grade(yrs:Int) = "Senior"
}

val sp = new SalesPerson
sp.grade(yrs=1)
//sp.grade(years=1) //error

val s:Person = new SalesPerson
s.grade(years=1)

//can be expression
s.grade(years={val x = 10; x+1})



//default arguments
//see Query class in mongo package

val skipOption = Skip(10, NoOption)
val newQuery = Query(new BasicDBObject, skipOption)

//copy method
//copy Skip but replace NoOption with Limit
val skipWithLimit = skipOption.copy(anotherOption = Limit(10, NoOption))

Skip(10, NoOption) == Skip(10, NoOption).copy()
