package com.tfr.scalaInAction.ch3.mongo
import com.mongodb.{DBCursor, DBObject}

/**
  * Created by Erik on 7/14/2017.
  */
trait LocaleAware extends ReadOnly {

  override def findOne(doc: DBObject) = {
    doc.put("locale", java.util.Locale.getDefault.getLanguage)
    super.findOne(doc)
  }

  override def find(doc: DBObject) = {
    doc.put("locale", java.util.Locale.getDefault.getLanguage)
    super.find(doc)
  }

}
