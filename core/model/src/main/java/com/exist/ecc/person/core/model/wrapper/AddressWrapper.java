package com.exist.ecc.person.core.model.wrapper;

import com.exist.ecc.person.core.model.Address;

import com.exist.ecc.person.util.StringUtil;

public class AddressWrapper {
  private final Address address;

  public AddressWrapper(Address address) {
    this.address = address;
  }

  public String getStreetAddress() {
    return address.getStreetAddress();
  }

  public String getBarangay() {
    return address.getBarangay();
  }

  public String getMunicipality() {
    return address.getMunicipality();
  }

  public String getZipCode() {
    return address.getZipCode();
  }

  public String getFullAddress() {
    String addressString = 
      new StringBuilder()
      .append(StringUtil.formatUnlessBlank("%s, ", 
        address.getStreetAddress()))
      .append(StringUtil.formatUnlessBlank("%s, ", 
        address.getBarangay()))
      .append(StringUtil.formatUnlessBlank("%s ", 
        address.getMunicipality()))
      .append(StringUtil.formatUnlessBlank("%s", 
        address.getZipCode()))
      .toString();

    return addressString;
  }

}