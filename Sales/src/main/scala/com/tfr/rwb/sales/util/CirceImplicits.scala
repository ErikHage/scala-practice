package com.tfr.rwb.sales.util

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

import com.tfr.rwb.sales.model.SaleDayModel.SalesDay
import com.tfr.rwb.sales.model.SaleItemModel.SaleItem
import com.tfr.rwb.sales.model.SaleModel.Sale
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.java8.time.decodeLocalDateTime
import io.circe.java8.time.encodeLocalDateTime
import io.circe.java8.time.decodeLocalDate
import io.circe.java8.time.encodeLocalDate

/**
  * Created by Erik on 8/16/2017.
  */
object CirceImplicits {

  val dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  implicit val saleItemDecoder: Decoder[SaleItem] = deriveDecoder[SaleItem]
  implicit val saleItemEncoder: Encoder[SaleItem] = deriveEncoder[SaleItem]

  implicit val saleDecoder: Decoder[Sale] = deriveDecoder[Sale]
  implicit val saleEncoder: Encoder[Sale] = deriveEncoder[Sale]

  implicit val salesDayDecoder: Decoder[SalesDay] = deriveDecoder[SalesDay]
  implicit val salesDayEncoder: Encoder[SalesDay] = deriveEncoder[SalesDay]

  implicit val localDateTimeDecoder: Decoder[LocalDateTime] = decodeLocalDateTime(dateTimeFormat)
  implicit val localDateTimeEncoder: Encoder[LocalDateTime] = encodeLocalDateTime(dateTimeFormat)

  implicit val localDateDecoder: Decoder[LocalDate] = decodeLocalDate(dateFormat)
  implicit val localDateEncoder: Encoder[LocalDate] = encodeLocalDate(dateFormat)

}
