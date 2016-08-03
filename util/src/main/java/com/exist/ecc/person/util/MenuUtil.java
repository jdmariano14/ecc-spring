package com.exist.ecc.person.util;

public class MenuUtil {
  
  public static String getMenu(String[] options) {
    StringBuilder menu = new StringBuilder();
    
    for (int index = 0; index < options.length; index++) {
      menu.append((index + 1) + ". ");
      menu.append(options[index]);
      if (index < options.length - 1) {
        menu.append(System.lineSeparator());
      }
    }

    return menu.toString();
  }

}