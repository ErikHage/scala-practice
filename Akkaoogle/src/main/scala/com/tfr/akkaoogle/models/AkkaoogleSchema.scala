package com.tfr.akkaoogle.models

import java.sql.DriverManager

import com.tfr.akkaoogle.models
import org.squeryl.adapters.H2Adapter
import org.squeryl.{PrimitiveTypeMode, Schema, Session, Table}


/**
  * Created by Erik on 8/5/2017.
  */
object AkkaoogleSchema extends Schema {

  val products: Table[models.Product] = table[models.Product]("PRODUCTS")
  val vendors: Table[ExternalVendor] = table[ExternalVendor]("VENDORS")
  val transactionFailures: Table[TransactionFailure] =
    table[TransactionFailure]("TRANSACTION_LOG")

  def init(): Unit = {
    import org.squeryl.SessionFactory
    Class.forName("org.h2.Driver")
    if(SessionFactory.concreteFactory.isEmpty) {
      SessionFactory.concreteFactory = Some(() =>
        Session.create(
          DriverManager.getConnection("jdbs:h2:tcp://localhost/~/test","sa",""),
          new H2Adapter
        )
      )
    }
  }

  def tx[A](a: =>A): A = {
    init()
    PrimitiveTypeMode.inTransaction(a)
  }

  def createSchema(): Unit = {
    tx {
      drop
      create
    }
  }

}
