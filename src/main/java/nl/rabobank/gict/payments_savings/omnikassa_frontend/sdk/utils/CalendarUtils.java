package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils;

import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CalendarUtils {
   private static final Logger LOGGER = LoggerFactory.getLogger(CalendarUtils.class);
   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

   private CalendarUtils() {
   }

   public static String calendarToString(Calendar calendar) {
      if (calendar == null) {
         return null;
      } else {
         DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER.withZone(DateTimeZone.forTimeZone(calendar.getTimeZone()));
         DateTime dateTime = new DateTime(calendar.getTime());
         return dateTime.toString(dateTimeFormatter);
      }
   }

   public static Calendar stringToCalendar(String date) {
      if (date == null) {
         return null;
      } else {
         Calendar calendar = Calendar.getInstance();

         try {
            return getCalendar(DATE_TIME_FORMATTER, date, calendar);
         } catch (IllegalFieldValueException var3) {
            LOGGER.warn("Could not convert date string to calender", var3);
            return null;
         }
      }
   }

   private static Calendar getCalendar(DateTimeFormatter dateFormat, String date, Calendar calendar) {
      dateFormat.withZone(DateTimeZone.forTimeZone(calendar.getTimeZone()));
      calendar.setTime((new DateTime(date)).toDate());
      return calendar;
   }
}
