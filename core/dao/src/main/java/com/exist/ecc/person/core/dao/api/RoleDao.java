package com.exist.ecc.person.core.dao.api;

import java.util.List;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

public interface RoleDao extends Dao<Role, Long> {
  
  public abstract List<Role> getAllById();

  public abstract List<Role> getAllGrantable(Person person);
  
}
