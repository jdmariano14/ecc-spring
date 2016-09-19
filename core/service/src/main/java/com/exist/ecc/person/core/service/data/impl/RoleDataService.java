package com.exist.ecc.person.core.service.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.model.Role;

public class RoleDataService extends AbstractDataService<RoleDto, Long> {

  private RoleCriteriaDao roleDao;

  public void setRoleDao(RoleCriteriaDao roleDao) {
    this.roleDao = roleDao;
  }

  @Override
  public RoleDto get(Long id) {
    return roleDao.get(id).getDto();
  }
  
  @Override
  public List<RoleDto> getAll() {
    return roleDao.query(c -> c.addOrder(Order.asc("roleId")))
                  .stream()
                  .map(r -> r.getDto())
                  .collect(Collectors.toList());
  }

  @Override
  public void save(RoleDto dto) {
    Role role = null;

    try {
      role = roleDao.get(dto.getRoleId());
      role.readDto(dto);
      roleDao.save(role);
    } catch (Exception e) {
      role = new Role();
      role.readDto(dto);
      roleDao.save(role);
    }
  }
  
  
  @Override
  public void delete(RoleDto dto) {
    Role role = roleDao.get(dto.getRoleId());

    role.getPersons().forEach(p -> p.getRoles().remove(role));
    role.getPersons().clear();

    roleDao.delete(role);
  }

  public List<RoleDto> getFromPerson(PersonDto personDto) {
    List<RoleDto> roleDtos;
    Set<Long> roleIds = personDto.getRoleIds();

    if (!roleIds.isEmpty()) {
      roleDtos = roleDao.query(c -> c.add(Restrictions.in("roleId", roleIds)))
                        .stream()
                        .map(r -> r.getDto())
                        .collect(Collectors.toList());
    } else {
      roleDtos = new ArrayList(0);
    }

    return roleDtos;
  }

}