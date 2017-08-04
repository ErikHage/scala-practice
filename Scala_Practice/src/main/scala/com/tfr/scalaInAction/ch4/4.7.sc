//collection hierarchy

//mutable and immutable collections share a parent
//mutable class adds mutation methods (like -= and +=)
//make sure to make obvious in your code which is being used

//scala automatically imports immutable collections

//three main types: Set, Seq, Map

//Traversable < Iterable < Seq, Set, Map
//  Set < SortedSet
//  Seq < Buffer, Vector, (LinearSeq < List)
//  Map < SortedMap

//only abstract method in Traversable is foreach
//other methods:
//  size, ++, map, flatMap, filter, find, /:, :\, head, tail, mkString

//how to wrap any java collection as Traversable
import java.util
import java.util.{Collection => JCollection}

class JavaToTraversable[E](javaCollection: JCollection[E]) extends Traversable[E] {
  override def foreach[U](f: (E) => U): Unit = {
    val iterator = javaCollection.iterator
    while(iterator.hasNext) {
      f(iterator.next)
    }
  }
}

val jCol = new util.ArrayList[Int]
(1 to 5) foreach { jCol.add }
jCol

val jtoT = new JavaToTraversable(jCol)

jtoT map { _ * 10 } filter { _ > 20 }

//Traversable can be defined as finite or infinite (via hasDefiniteSize)