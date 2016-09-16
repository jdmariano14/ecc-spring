package com.exist.ecc.person.core.service.input.impl;

import java.util.List;

import org.hibernate.Session;

import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

public class RoleCsvInputService extends CsvInputService {
  
  public RoleCsvInputService(Session session) {
    RoleDataService roleDataService = new RoleDataService();
    roleDataService.setSession(session);
    setDataService(roleDataService);
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 2) {
      return;
    }

    Long roleId = Long.parseLong(values[0]);
    String name = values[1];

    RoleDto roleDto = new RoleDto(roleId, name, null);

    getDataService().save(roleDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<RoleDto> roleDtos = getDataService().getAll();

    for (RoleDto roleDto : roleDtos) {
      getDataService().delete(roleDto);
    }
  }

}