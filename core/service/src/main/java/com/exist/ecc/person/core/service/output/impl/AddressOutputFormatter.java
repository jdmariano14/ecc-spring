package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Address;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class AddressOutputFormatter implements OutputFormatter<Address> {

  public String format(Address address) {
    String addressString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessEmpty(
        address.getStreetAddress(), "%s, "))
      .append(StringUtil.formatUnlessEmpty(address.getBarangay(), "%s, "))
      .append(StringUtil.formatUnlessEmpty(address.getMunicipality(), "%s "))
      .append(StringUtil.formatUnlessEmpty(address.getZipCode(), "%s"))
      .toString();

    return addressString;
  }

}