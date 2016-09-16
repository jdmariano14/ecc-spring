package com.exist.ecc.person.core.service.input;

import org.hibernate.Session;

import com.exist.ecc.person.core.service.input.api.InputService;
import com.exist.ecc.person.core.service.input.impl.*;

public class InputServiceFactory {

  public InputService get(Session session, String type, String extension) {
    InputService inputService = null;

    switch (extension.toLowerCase()) {
      case "csv":
        switch(type.toLowerCase()) {
          case "persons":
            inputService = new PersonCsvInputService(session);
            break;
          case "roles":
            inputService = new RoleCsvInputService(session);
            break;
          case "contacts":
            inputService = new ContactCsvInputService(session);
            break;
          case "person roles":
            inputService = new PersonRoleCsvInputService(session);
            break;
        }
        break;
    }

    return inputService;
  }

}