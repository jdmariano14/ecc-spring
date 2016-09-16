package com.exist.ecc.person.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class DateUtil {
  
  public static DateFormat getDateFormat() {
    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    
    return dateFormat;
  }

  public static Date parse(String dateString) {
    return parse(getDateFormat(), dateString);
  }

  public static Date parse(DateFormat dateFormat, String dateString) {
    Date date = null;

    try {
      date = dateFormat.parse(dateString);
    } catch (NullPointerException | ParseException e) {
      
    }

    return date;
  }

}