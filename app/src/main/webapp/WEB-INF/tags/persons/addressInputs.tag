<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="personAddress" required="true" type="com.exist.ecc.person.core.model.Address"%>

<fieldset>
  <legend>Address</legend>
  <label for="person[address[street_address]]">Street address</label>
  <input type="text" name="person[address[street_address]]" value="${personAddress.streetAddress}">
  <label for="person[address[barangay]]">Barangay</label>
  <input type="text" name="person[address[barangay]]" value="${personAddress.barangay}">
  <label for="person[address[municipality]]">Municipality</label>
  <input type="text" name="person[address[municipality]]" value="${personAddress.municipality}">
  <label for="person[address[zip_code]]">Zip code</label>
  <input type="text" name="person[address[zip_code]]" value="${personAddress.zipCode}">
</fieldset>