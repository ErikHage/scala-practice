package com.tfr.rwb.sales.parse

import com.tfr.rwb.sales.model.SaleDayModel.SalesDay
import com.tfr.rwb.sales.util.JsonUtil
import com.tfr.rwb.sales.util.CirceImplicits._

/**
  * Created by Erik Hage on 8/12/2017.
  */
object DailySalesParser {

  def parseSalesDay(salesDay: String): Option[SalesDay] = {
    JsonUtil.fromJson[SalesDay](salesDay)
  }

}
