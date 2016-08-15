package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Address;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.ReturnNullHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class AddressWizard extends AbstractWizard<Address> {

  public AddressWizard(InputReader reader, InputExceptionHandler handler) {
    super(reader, handler);
  }

  public void initializeData(Map<String, PropertyData> data) {
    String[] optionalStrings = {
      "streetAddress", "barangay", "municipality", "zipCode"};

    for (String optionalString : optionalStrings) {
      data.put(optionalString, new PropertyData(
        new InputService.Builder<String>(getReader(), getDefaultHandler())
        .message(optionalString)
        .validation(Validations.get(Address.class, optionalString))));
    }
        
  }

}