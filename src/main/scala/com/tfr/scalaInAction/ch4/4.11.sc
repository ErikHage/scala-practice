//Parallel collections

//basic idea:
//split collections into smaller pieces, do operation, combine them back into one collection

import collection.parallel.immutable._

val pv = ParVector(10,20,30,40,50,60,70,80,90).map{
  x => println(Thread.currentThread().getName) //shows up in REPL
  x/2
}

//change the task support for a collection by setting
pv.tasksupport // = new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(4))

//no order of execution is promised
//map and filter are good operations to use
//foldLeft is not (will only use single thread)


//----------Hierarchy

//ParIterable < ParSeq, ParSet, ParMap
//  ParSet < ParHashSet
//  ParSeq < ParVector, ParArray, ParRange
//  ParMap < ParHashSet

//Gen* classes in scala.collection package implement operations that could be implemented in both a sequential and parallel collection library

import scala.collection.parallel.mutable.ParArray
ParArray(1,2,3,4).sum


//-------------Switch between sequential and parallel

//.par and .seq methods
val vs = Vector.range(1,20)
vs.par.filter(_ % 2 == 0)

Vector.range(1,10).par.filter(_ % 2 == 0).seq