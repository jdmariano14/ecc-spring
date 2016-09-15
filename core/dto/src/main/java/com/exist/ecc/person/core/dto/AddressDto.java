package com.exist.ecc.person.core.dto;

public class AddressDto {

  private String streetAddress;
  private String barangay;
  private String municipality;
  private String zipCode;

  public AddressDto(String streetAddress,
                    String barangay,
                    String municipality,
                    String zipCode)
  {
    setStreetAddress(streetAddress);
    setBarangay(barangay);
    setMunicipality(municipality);
    setZipCode(zipCode);
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getBarangay() {
    return barangay;
  }

  public void setBarangay(String barangay) {
    this.barangay = barangay;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

}