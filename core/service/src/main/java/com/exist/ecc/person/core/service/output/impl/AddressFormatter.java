package com.exist.ecc.person.core.service.output.impl;

import com.exist.ecc.person.core.model.Address;

import com.exist.ecc.person.core.service.output.api.OutputFormatter;

import com.exist.ecc.person.util.StringUtil;

public class AddressFormatter implements OutputFormatter<Address> {

  public String format(Address address) {
    String addressString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank(
        "%s, ", address.getStreetAddress()))
      .append(StringUtil.formatUnlessBlank("%s, ", address.getBarangay()))
      .append(StringUtil.formatUnlessBlank("%s ", address.getMunicipality()))
      .append(StringUtil.formatUnlessBlank("%s", address.getZipCode()))
      .toString();

    return addressString;
  }

}