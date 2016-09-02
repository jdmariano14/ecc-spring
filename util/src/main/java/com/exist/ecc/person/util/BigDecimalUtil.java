package com.exist.ecc.person.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

  public static BigDecimal parse(String bigDecimalString) {
    BigDecimal bigDecimal = null;

    try {
      bigDecimal = new BigDecimal(bigDecimalString);
    } catch (NullPointerException | NumberFormatException e) {
      
    }

    return bigDecimal;
  }

}