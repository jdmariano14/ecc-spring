package com.exist.ecc.person.core.service.input.impl;

import java.util.List;

import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

public class RoleCsvInputService extends CsvInputService {

  private RoleDataService roleDataService;
  
  public RoleCsvInputService(RoleDataService roleDataService) {
    this.roleDataService = roleDataService;
  }

  @Override
  public void processValueArray(String[] values) {
    if (values.length != 2) {
      return;
    }

    System.out.println("Kalaber");

    Long roleId = Long.parseLong(values[0]);
    String name = values[1];

    RoleDto roleDto = new RoleDto(roleId, name, null);

    roleDataService.save(roleDto);
  }

  @Override
  public void clearDatabaseTable() {
    List<RoleDto> roleDtos = roleDataService.getAll();

    for (RoleDto roleDto : roleDtos) {
      roleDataService.delete(roleDto);
    }
  }

}