package com.tfr.akkaoogle.models

import AkkaoogleSchema._
import org.squeryl.PrimitiveTypeMode._

/**
  * Created by Erik on 8/4/2017.
  */
class ExternalVendor(val name: String, val url: String) extends Model[ExternalVendor] {

}
object ExternalVendor {
  def findAll: Iterable[ExternalVendor] = tx {
    from(vendors)(s => select(s)) map(s => s)
  }
}