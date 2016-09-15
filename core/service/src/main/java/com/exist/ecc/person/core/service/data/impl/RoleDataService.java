package com.exist.ecc.person.core.service.data.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.exist.ecc.person.core.dao.api.Dao;
import com.exist.ecc.person.core.dao.impl.CriteriaDaoImpl;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.model.Role;

public class RoleDataService extends AbstractDataService<RoleDto, Long> {

  // @Autowired
  private Dao<Role, Long> roleDao = new CriteriaDaoImpl();

  @Override
  public RoleDto get(Long id) {
    return roleDao.get(id).getDto();
  }
  
  @Override
  public List<RoleDto> getAll() {
    return roleDao.getAll().stream()
                  .map(r -> r.getDto())
                  .collect(Collectors.toList());
  }

  @Override
  public void save(RoleDto dto) {
    Role role = roleDao.get(dto.getRoleId());
    role.readDto(dto);
    roleDao.save(role);
  }
  
  @Override
  public void delete(RoleDto dto) {
    Role role = roleDao.get(dto.getRoleId());
    roleDao.delete(role);
  }

}