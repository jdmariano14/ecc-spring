package com.exist.ecc.person.core.service.data.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.criterion.Order;

import com.exist.ecc.person.core.dao.api.CriteriaDao;
import com.exist.ecc.person.core.dao.impl.CriteriaDaoImpl;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.model.Role;

public class RoleDataService extends AbstractDataService<RoleDto, Long> {

  // @Autowired
  private CriteriaDao<Role, Long> roleDao = new CriteriaDaoImpl();

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
    Role role = roleDao.get(dto.getRoleId());
    
    role.readDto(dto);
    roleDao.save(role);
  }
  
  @Override
  public void delete(RoleDto dto) {
    Role role = roleDao.get(dto.getRoleId());

    role.getPersons().forEach(p -> p.getRoles().remove(role));
    role.getPersons().clear();

    roleDao.delete(role);
  }

}