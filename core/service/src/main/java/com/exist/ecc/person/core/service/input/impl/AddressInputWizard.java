package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Address;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExtractor;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class AddressInputWizard extends AbstractInputWizard<Address> {

  public AddressInputWizard(InputExtractor extractor, 
    InputExceptionHandler exceptionHandler) {
    super(extractor, exceptionHandler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] stringProperties = {
      "streetAddress", "barangay", "municipality", "zipCode"};

    for (String stringProperty : stringProperties) {
      data.put(stringProperty, new PropertyData(
        new InputService.Builder<String>(getExtractor(), getExceptionHandler())
        .message(stringProperty)
        .validation(Validations.get(Address.class, stringProperty))));
    }
        
  }

}