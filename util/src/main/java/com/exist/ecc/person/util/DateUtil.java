package com.exist.ecc.person.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;

public class DateUtil {
  
  public static DateFormat getDateFormat() {
    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    
    return dateFormat;
  }

}