package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Name;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExtractor;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class NameInputWizard extends AbstractInputWizard<Name> {

  public NameInputWizard(InputExtractor extractor, 
    InputExceptionHandler exceptionHandler) {
    super(extractor, exceptionHandler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] stringProperties = {
      "lastName", "firstName", "middleName", "suffix", "title"};

    for (String stringProperty : stringProperties) {
      data.put(stringProperty, new PropertyData(
        new InputService.Builder<String>(getExtractor(), getExceptionHandler())
        .message(stringProperty)
        .validation(Validations.get(Name.class, stringProperty))));
    }

    
  }

}