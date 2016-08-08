package com.exist.ecc.person.util;

import java.util.function.UnaryOperator;

public class MenuUtil {
  
  public static String getMenu(String[] options, 
    UnaryOperator<String> stringOp) 
  {
    StringBuilder menu = new StringBuilder();
    
    for (int index = 0; index < options.length; index++) {
      menu.append((index + 1) + ". ");
      menu.append(stringOp.apply(options[index]));
      if (index < options.length - 1) {
        menu.append(System.lineSeparator());
      }
    }

    return menu.toString();
  }

}