package com.tfr.akkaoogle.models

import java.util.Date

import AkkaoogleSchema._
import org.squeryl.PrimitiveTypeMode._

/**
  * Created by Erik on 8/4/2017.
  */
class TransactionFailure(val vendorId: String, val message: String, val timestamp: Date) extends Model[TransactionFailure] {

}
object TransactionFailure {
  def findAll: Iterable[TransactionFailure] = tx {
    from(transactionFailures)(s => select(s)) map(s => s)
  }
}

