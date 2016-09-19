package com.exist.ecc.person.core.service.input;

import org.hibernate.Session;

import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;
import com.exist.ecc.person.core.service.input.api.InputService;
import com.exist.ecc.person.core.service.input.impl.*;

public class InputServiceFactory {

  private PersonDataService personDataService;
  private RoleDataService roleDataService;
  private ContactDataService contactDataService;

  public void setPersonDataService(PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  public void setRoleDataService(RoleDataService roleDataService) {
    this.roleDataService = roleDataService;
  }

  public void setContactDataService(ContactDataService contactDataService) {
    this.contactDataService = contactDataService;
  }

  public InputService get(Session session, String type, String extension) {
    InputService inputService = null;

    switch (extension.toLowerCase()) {
      case "csv":
        switch(type.toLowerCase()) {
          case "persons":
            inputService = new PersonCsvInputService(personDataService);
            break;
          case "roles":
            inputService = new RoleCsvInputService(roleDataService);
            break;
          case "contacts":
            inputService = new ContactCsvInputService(contactDataService);
            break;
          case "person roles":
            inputService = new PersonRoleCsvInputService(personDataService);
            break;
        }
        break;
    }

    return inputService;
  }

}