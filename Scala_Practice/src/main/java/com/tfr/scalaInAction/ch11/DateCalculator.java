package com.tfr.scalaInAction.ch11;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Days;
import java.util.Date;

/**
 *
 * Created by Erik on 8/3/2017.
 */
public class DateCalculator {
    public int daysBetween(Date start, Date end) {
        Days d = Days.daysBetween(new DateTime(start.getTime()),
                new DateTime(end.getTime()));
        return d.getDays();
    }
    public static Chronology getChronologyUsed() {
        return DateTime.now().getChronology();
    }
}