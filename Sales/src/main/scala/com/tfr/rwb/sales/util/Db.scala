package com.tfr.rwb.sales.util

import slick.jdbc.H2Profile.api._

/**
  * Created by Erik on 8/21/2017.
  */
trait Db {

  def db = Database.forConfig("h2mem1")

  def close(db: Database) = db.close

}
