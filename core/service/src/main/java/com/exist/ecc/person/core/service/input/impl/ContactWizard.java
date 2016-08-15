package com.exist.ecc.person.core.service.input.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.exist.ecc.person.core.model.Contact;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.RepeatReadHandler;
import com.exist.ecc.person.core.service.input.impl.ReturnNullHandler;
import com.exist.ecc.person.core.service.validation.Validations;

public class ContactWizard<T extends Contact> extends AbstractWizard<T> {

  private Class<T> contactType;

  public ContactWizard(Class<T> contactType, InputReader reader, 
    InputExceptionHandler handler) 
  {
    super(reader, handler);
    this.contactType = contactType;

    initializeDataWithType(getData());
  }

  public void initializeData(Map<String, PropertyData> data) {
    
  }

  public void initializeDataWithType(Map<String, PropertyData> data) {
    String[] requiredStrings = { "info" };

    for (String requiredString : requiredStrings) {
      data.put(requiredString, new PropertyData(
        new InputService.Builder<String>(getReader(), new RepeatReadHandler())
        .message(requiredString)
        .validation(Validations.get(contactType, requiredString))));
    }
  }

}