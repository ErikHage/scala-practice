package com.tfr.rwb.sales.util

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime, ZoneOffset}

import slick.jdbc.H2Profile.api._

/**
  * Created by Erik Hage on 8/27/2017.
  */
trait SlickColumnMappings {

  implicit def dateColumnType = MappedColumnType.base[LocalDate, Date](
    l => Date.valueOf(l),
    d => d.toLocalDate
  )

  implicit def localDateTimeColumnType = MappedColumnType.base[LocalDateTime, Timestamp](
    l => Timestamp.from(l.toInstant(ZoneOffset.ofHours(0))),
    d => d.toLocalDateTime
  )

}
