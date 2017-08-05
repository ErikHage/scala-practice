package com.tfr.akkaoogle

import java.util.concurrent.TimeUnit

import akka.util.Timeout

/**
  * Created by Erik on 8/5/2017.
  */
package object http {
  implicit val timeout = Timeout(150, TimeUnit.MILLISECONDS)
}
