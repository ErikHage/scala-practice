package com.tfr.rwb.sales.util

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime, ZoneOffset}

import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/27/2017.
  */
trait SlickColumnMappings {

  implicit val dateColumnType = MappedColumnType.base[LocalDate, Date](
    d => Date.valueOf(d),
    d => d.toLocalDate
  )

  implicit val localDateTimeColumnType = MappedColumnType.base[LocalDateTime, Timestamp](
    d => Timestamp.from(d.toInstant(ZoneOffset.ofHours(0))),
    d => d.toLocalDateTime
  )

}
