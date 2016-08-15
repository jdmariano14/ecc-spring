package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Name;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.RepeatReadHandler;
import com.exist.ecc.person.core.service.input.impl.ReturnNullHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class NameWizard extends AbstractWizard<Name> {

  public NameWizard(InputReader reader, InputExceptionHandler handler) {
    super(reader, handler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] requiredStrings = {
      "lastName", "firstName"};
    String[] optionalStrings = {
      "middleName", "suffix", "title"};

    for (String requiredString : requiredStrings) {
      data.put(requiredString, new PropertyData(
        new InputService.Builder<String>(getReader(), new RepeatReadHandler())
        .message(requiredString)
        .validation(Validations.get(Name.class, requiredString))));
    }

    for (String optionalString : optionalStrings) {
      data.put(optionalString, new PropertyData(
        new InputService.Builder<String>(getReader(), new ReturnNullHandler())
        .message(optionalString)
        .validation(Validations.get(Name.class, optionalString))));
    }
    
  }

}