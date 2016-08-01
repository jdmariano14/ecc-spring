package com.exist.ecc.person.core.model;

public class Address {

  private String streetAddress;

  private String barangay;

  private String municipality;

  private String zipCode;

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String newStreetAddress) {
    streetAddress = newStreetAddress;
  }

  public String getBarangay() {
    return barangay;
  }

  public void setBarangay(String newBarangay) {
    barangay = newBarangay;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String newMunicipality) {
    municipality = newMunicipality;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String newZipCode) {
    zipCode = newZipCode;
  }

}