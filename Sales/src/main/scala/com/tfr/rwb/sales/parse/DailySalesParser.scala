package com.tfr.rwb.sales.parse

import com.tfr.rwb.sales.model._
import com.tfr.rwb.sales.util.JsonUtil

/**
  * Created by Erik Hage on 8/12/2017.
  */
object DailySalesParser {

  def parseSalesDay(json: String): SalesDay = {
    JsonUtil.fromJson[SalesDay](json)
  }

}
