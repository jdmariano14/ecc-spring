package com.exist.ecc.person.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

  public static String camelCaseToSpaces(String str) {
    return str.replaceAll("(.)(\\p{Upper})", "$1 $2");
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

  public static String spaces(int count) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < count; i++) {
      sb.append(" ");
    }

    return sb.toString();
  }

  public static String lineBreaks(int count) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < count; i++) {
      sb.append(System.lineSeparator());
    }

    return sb.toString();
  }

  public static String formatUnlessBlank(String format, Object... args) {
    for (Object arg : args) {
      if (arg == null || StringUtils.isBlank(arg.toString())) {
        return "";
      }
    }

    return String.format(format, args);
  }

  public static int indexOfPattern(String str, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);

    while(matcher.find()) {
      return matcher.start();
    }

    return -1;
  }

}