package com.tfr.scalaInAction.ch3.mongo

import com.mongodb.Mongo

/**
  * Created by Erik on 7/14/2017.
  */
class MongoClient(private val host:String, private val port:Int) {
  require(host != null, "You must provide a host name")
  private val underlying = new Mongo(host, port)

  def this() = this("127.0.0.1", 27017)

  def version = underlying.getVersion
  def dropDB(name:String) = underlying.dropDatabase(name)
  def createDB(name:String) = DB(underlying.getDB(name))
  def db(name:String) = DB(underlying.getDB(name))

}
