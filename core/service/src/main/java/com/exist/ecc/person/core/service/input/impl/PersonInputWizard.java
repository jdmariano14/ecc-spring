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
    InputExceptionHandler handler) {
    super(reader, handler);
    dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] optionalDates = {"birthDate", "dateHired"};
    String[] optionalBigDecimals = {"gwa"};
    String[] optionalBooleans = {"employed"};

    for (String optionalDate : optionalDates) {
      data.put(optionalDate, new PropertyData(
        new InputService
        .Builder<Date>(getReader(), new ReturnNullHandler())
        .message(optionalDate)
        .conversion(s -> { 
          try {
            return dateFormat.parse(s);
          } catch(ParseException e) {
            throw new RuntimeException(e);
          }
        })
        .validation(Validations.get(Person.class, optionalDate))));
    }

    for (String optionalBoolean : optionalBooleans) {
      data.put(optionalBoolean, new PropertyData(
        new InputService
        .Builder<Boolean>(getReader(), new ReturnNullHandler())
        .message(optionalBoolean)
        .conversion(b -> {
          return Boolean.valueOf(b) 
                 || b.toString().equalsIgnoreCase("t")
                 || b.toString().equalsIgnoreCase("y");
        })
        .validation(Validations.get(Person.class, optionalBoolean))));
    }

    for (String optionalBigDecimal : optionalBigDecimals) {
      data.put(optionalBigDecimal, new PropertyData(
        new InputService
        .Builder<BigDecimal>(getReader(), new ReturnNullHandler())
        .message(optionalBigDecimal)
        .conversion(s -> { return new BigDecimal(s); })
        .validation(Validations.get(Person.class, optionalBigDecimal))));
    }
  }

}