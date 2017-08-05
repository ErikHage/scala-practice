package com.tfr.akkaoogle

import org.squeryl.Table

/**
  * Created by Erik on 8/5/2017.
  */
package object models {
  implicit val transactionFailures: Table[TransactionFailure] = transactionFailures
  implicit val vendors: Table[ExternalVendor] = vendors
  implicit val products: Table[Product] = products
}
