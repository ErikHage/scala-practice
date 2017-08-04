package com.tfr.scalaInAction.ch3.mongo

import com.mongodb.{DB => MongoDB}

import scala.collection.convert.Wrappers.JSetWrapper
/**
  * Created by Erik on 7/14/2017.
  */
class DB private(val underlying:MongoDB) {
  private def collection(name:String) = underlying.getCollection(name)

  def readOnlyCollection(name:String) = new DBCollection(collection(name)) with Memoizer
  def administrableCollection(name:String) = new DBCollection(collection(name)) with Administrable with Memoizer
  def updatableCollection(name:String) = new DBCollection(collection(name)) with Updatable with Memoizer

  def collectionNames = for(name <- new JSetWrapper(underlying.getCollectionNames)) yield name
}

object DB {
  def apply(underlying:MongoDB) = new DB(underlying)
}
