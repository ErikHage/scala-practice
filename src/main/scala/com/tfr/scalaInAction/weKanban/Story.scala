package com.tfr.scalaInAction.weKanban

import com.tfr.scalaInAction.weKanban.model.KanbanSchema._

import org.squeryl.PrimitiveTypeMode._

/**
  * Created by Erik on 7/28/2017.
  */
class Story(val number:String, val title:String, val phase:String) {

  private[this] def validate() = {
    if(number.isEmpty || title.isEmpty) {
      throw new ValidationException("Both number and title are rquired")
    }
    if(stories.where(a => a.number === number).nonEmpty) {
      throw new ValidationException("The story number is not unique")
    }
  }

  def save(): Either[Throwable, String] = {
    tx {
      try {
        validate()
        stories.insert(this)
        Right("Story is created successfully")
      } catch {
        case exception: Throwable => Left(exception)
      }
    }
  }
}
object Story {
  def apply(number:String, title:String) = new Story(number, title, "ready")
}