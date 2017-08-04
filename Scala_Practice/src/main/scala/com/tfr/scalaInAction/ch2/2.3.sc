val array = new Array[String](3)

array(0) = "This"
array(1) = "is"
array(2) = "mutable"

array

array.foreach(println)

val myList = List("This","is","immutable")

val oldList = List(1,2)
val newList = 3 :: oldList
val newList2 = oldList :+ 3

val myList2 = "this" :: "is" :: "immutable" :: Nil

val afterDelete = newList.filterNot(_ == 3)