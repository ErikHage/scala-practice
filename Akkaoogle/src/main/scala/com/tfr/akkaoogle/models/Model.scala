package com.tfr.akkaoogle.models

import AkkaoogleSchema._
import org.squeryl.{KeyedEntity, Table}

/**
  * Created by Erik on 8/4/2017.
  */
trait Model[A] extends KeyedEntity[Long] { this: A =>
  val id: Long = 0

  def save(implicit table: Table[A]): Either[Throwable, String] = {
    tx {
      try {
        table.insert(this)
        Right("Domain object is saved successfully")
      } catch {
        case exception: Throwable => Left(exception)
      }
    }
  }
}