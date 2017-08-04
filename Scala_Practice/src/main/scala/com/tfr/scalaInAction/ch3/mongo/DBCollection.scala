package com.tfr.scalaInAction.ch3.mongo

import com.mongodb.{DBCursor, DBObject, DBCollection => MongoDBCollection}

/**
  * Created by Erik on 7/14/2017.
  */
class DBCollection (override val underlying: MongoDBCollection) extends ReadOnly {

}

trait ReadOnly {
  val underlying: MongoDBCollection

  def name = underlying getName
  def fullName = underlying getFullName
  def find(doc:DBObject) = underlying find doc
  def findOne = underlying findOne
  def findOne(doc:DBObject) = underlying findOne doc
  def getCount(doc:DBObject) = underlying getCount doc

  def find(query: Query):DBCursor = {
    def applyOptions(cursor:DBCursor, option:QueryOption): DBCursor = {
      option match {
        case Skip(skip, next) => applyOptions(cursor.skip(skip), next)
        case Sort(sorting, next) => applyOptions(cursor.sort(sorting), next)
        case Limit(limit, next) => applyOptions(cursor.limit(limit), next)
        case NoOption => cursor
      }
    }
    applyOptions(find(query.q), query.option)
  }
}

trait Updatable extends ReadOnly {
  def -=(doc:DBObject): Unit = {
    underlying remove doc
  }
  def +=(doc:DBObject): Unit = {
    underlying save doc
  }
}

trait Administrable extends ReadOnly {
  def drop(): Unit = underlying drop()
  def dropIndexes(): Unit = underlying dropIndexes()
}
