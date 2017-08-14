package com.tfr.rwb.sales.util

import java.time.LocalDate

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}

/**
  * Created by Erik Hage on 8/13/2017.
  */
class LocalDateDeserializer extends JsonDeserializer[LocalDate] {
  override def deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDate = {
    LocalDate.parse(ctxt.toString)
  }
}
