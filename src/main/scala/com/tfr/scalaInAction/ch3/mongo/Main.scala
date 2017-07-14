package com.tfr.scalaInAction.ch3.mongo

import com.mongodb.BasicDBObject

/**
  * Created by Erik on 7/14/2017.
  */
object Main {

  def main(args: Array[String]): Unit = {

    val client = new MongoClient
    def db = client.createDB("mydb")

    db administrableCollection "number" drop()
    db administrableCollection "test" drop()

    for(name <- db.collectionNames) println(name)

    val col = db.readOnlyCollection("test")
    println("Readonly Collection:" + col.name)

    val adminCol = db.administrableCollection("test")
    println("Administrable Collection:" + adminCol.name)

    val updatableCol = db.updatableCollection("test")
    println("Updatable Collection:" + updatableCol.name)

    val doc = new BasicDBObject
    doc.put("name","MongoDB")
    doc.put("type","database")
    doc.put("count",1)

    val info = new BasicDBObject
    info.put("x",203)
    info.put("y",102)

    doc.put("info", info)

    updatableCol += doc

    println("After adding")
    println(updatableCol.findOne)

    updatableCol -= doc

    println("After removing")
    println(updatableCol.findOne)

    val updatableCol2 = db.updatableCollection("number")

    val query = new BasicDBObject("i", 71)

    for(i <- 1 to 100) updatableCol2 += new BasicDBObject("i", i)

    val col2 = db.readOnlyCollection("number")
    val cursor = col2 find query

    println("After adding 100 docs, query for #71")
    while(cursor.hasNext) {
      println("Result = " + cursor.next)
    }


    //Using query object

    val rangeQuery = new BasicDBObject("i", new BasicDBObject("$gt", 95))
    val richQuery = Query(rangeQuery).skip(1).limit(3)

    val cursor2 = col2.find(rangeQuery)
    val cursor3 = col2.find(richQuery)

    println("Range Query")
    while(cursor2.hasNext) {
      println(cursor2.next)
    }

    println("Rich Query")
    while(cursor3.hasNext) {
      println(cursor3.next)
    }








  }

}
