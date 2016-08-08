package com.exist.ecc.person.util;

public class StringUtil {

  public static String camelCaseToSpaces(String str) {
    return String.join(" ", str.split("\\p{Upper}\\p{Lower}+"));
  }

  public static String capitalize(String str) {
    StringBuilder sb = new StringBuilder();
    
    if (str.length() > 0) {
      sb.append(Character.toUpperCase(str.charAt(0)));
    }

    if (str.length() > 1) {
      sb.append(str.substring(1));
    }

    return sb.toString();
  }

  public static String getSpaces(int count) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < count; i++) {
      sb.append(" ");
    }

    return sb.toString();
  }


}