package com.tfr.rwb.sales.util

import io.circe.{Decoder, Encoder}
import io.circe.parser.parse
import io.circe.syntax._

import com.tfr.rwb.sales.util.CirceImplicits._

/**
  * Created by Erik Hage on 8/13/2017.
  */
object JsonUtil {

  def fromJson[T](json: String)(implicit decoder: Decoder[T]): Option[T] = {
    parse(json)
      .right
      .map(_.as[T])
      .map(_.toOption) match {
          case Right(success) => println("success!")
            success
          case Left(fail) => println(fail)
            None
    }
  }

  def toJson[T](obj: T)(implicit encoder: Encoder[T]): String = {
    obj.asJson.toString()
  }

}
