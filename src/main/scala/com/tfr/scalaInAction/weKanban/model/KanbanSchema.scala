package com.tfr.scalaInAction.weKanban.model

import java.sql.DriverManager

import com.tfr.scalaInAction.weKanban.Story
import org.squeryl.adapters.H2Adapter
import org.squeryl.{PrimitiveTypeMode, Schema, Session, Table}

/**
  * Created by Erik on 7/28/2017.
  */
object KanbanSchema extends Schema {
  val stories: Table[Story] = table[Story]("STORIES")

  def init(): Unit = {
    import org.squeryl.SessionFactory
    Class.forName("org.h2.Driver")
    if(SessionFactory.concreteFactory.isEmpty) {
      SessionFactory.concreteFactory = Some(() =>
      Session.create(
        DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", ""),
        new H2Adapter))
    }
  }

  def tx[A](a: =>A): A = {
    init()
    PrimitiveTypeMode.inTransaction(a)
  }

  def main(args: Array[String]): Unit = {
    println("initializing the weKanban schema")
    init()
    PrimitiveTypeMode.inTransaction { drop ; create }
  }
}
