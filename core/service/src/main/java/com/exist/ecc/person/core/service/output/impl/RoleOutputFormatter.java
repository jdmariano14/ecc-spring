package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class RoleOutputFormatter implements OutputFormatter<Role> {

  public String format(Role role) {
    return StringUtil.formatUnlessBlank("%s", role.getName());
  }

}