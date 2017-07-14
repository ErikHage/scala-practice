package com.tfr.scalaInAction.ch3.mongo

import com.mongodb.DBObject

/**
  * Created by Erik on 7/14/2017.
  */
case class Query(q:DBObject, option: QueryOption = NoOption) {
  def sort(sorting:DBObject) = Query(q, Sort(sorting, option))
  def skip(skip:Int) = Query(q, Skip(skip, option))
  def limit(limit:Int) = Query(q, Limit(limit, option))
}

