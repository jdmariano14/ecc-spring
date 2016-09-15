package com.exist.ecc.person.core.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.exist.ecc.person.core.dto.AddressDto;

@Embeddable
public class Address {

  @Column(name = "STREET_ADDRESS")
  private String streetAddress;

  @Column(name = "BARANGAY")
  private String barangay;

  @Column(name = "MUNICIPALITY")
  private String municipality;

  @Column(name = "ZIP_CODE")
  private String zipCode;

  public String getStreetAddress() { return streetAddress; }
  public String getBarangay() {return barangay; }
  public String getMunicipality() { return municipality; }
  public String getZipCode() { return zipCode; }

  public AddressDto getDto() {
    return new AddressDto(this.getStreetAddress(),
                          this.getBarangay(),
                          this.getMunicipality(),
                          this.getZipCode());
  }

  public void readDto(AddressDto dto) {
    this.streetAddress = dto.getStreetAddress();
    this.barangay = dto.getBarangay();
    this.municipality = dto.getMunicipality();
    this.zipCode = dto.getZipCode();
  }

}