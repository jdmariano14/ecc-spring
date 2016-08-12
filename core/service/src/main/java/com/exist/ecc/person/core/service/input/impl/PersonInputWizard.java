package com.exist.ecc.person.core.service.input.impl;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.ReturnNullHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class PersonInputWizard extends AbstractInputWizard<Person> {

  private DateFormat dateFormat;

  public PersonInputWizard(InputReader reader, 
    InputExceptionHandler exceptionHandler) {
    super(reader, exceptionHandler);
    dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] dateProperties = {"birthDate", "dateHired"};
    String[] bigDecimalProperties = {"gwa"};
    String[] booleanProperties = {"employed"};

    for (String dateProperty : dateProperties) {
      data.put(dateProperty, new PropertyData(
        new InputService
        .Builder<Date>(getReader(), getExceptionHandler())
        .message(dateProperty)
        .conversion(s -> { 
          try {
            return dateFormat.parse(s);
          } catch(ParseException e) {
            throw new RuntimeException(e.getMessage());
          }
        })
        .validation(Validations.get(Person.class, dateProperty))));
    }

    for (String booleanProperty : booleanProperties) {
      data.put(booleanProperty, new PropertyData(
        new InputService
        .Builder<Boolean>(getReader(), getExceptionHandler())
        .message(booleanProperty)
        .conversion(b -> {
          return Boolean.valueOf(b) 
                 || b.toString().equalsIgnoreCase("t")
                 || b.toString().equalsIgnoreCase("y");
        })
        .validation(Validations.get(Person.class, booleanProperty))));
    }

    for (String bigDecimalProperty : bigDecimalProperties) {
      data.put(bigDecimalProperty, new PropertyData(
        new InputService
        .Builder<BigDecimal>(getReader(), getExceptionHandler())
        .message(bigDecimalProperty)
        .conversion(s -> { return new BigDecimal(s); })
        .validation(Validations.get(Person.class, bigDecimalProperty))));
    }
  }

}