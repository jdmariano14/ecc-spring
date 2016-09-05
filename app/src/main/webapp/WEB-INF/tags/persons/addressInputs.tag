<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="personAddress" required="true" type="com.exist.ecc.person.core.model.Address"%>

<h3 class="ui dividing header">Address</h3>
<div class="field">
  <label for="person[address[street_address]]">Street</label>
  <input type="text" name="person[address[street_address]]" value="${personAddress.streetAddress}">
</div>
<div class="two fields">
  <div class="field">
    <label for="person[address[barangay]]">Barangay</label>
    <input type="text" name="person[address[barangay]]" value="${personAddress.barangay}">
  </div>
  <div class="field">
    <label for="person[address[municipality]]">Municipality</label>
    <input type="text" name="person[address[municipality]]" value="${personAddress.municipality}">
  </div>
</div>
<div class="three wide field">
  <label for="person[address[zip_code]]">Zip</label>
  <input type="text" name="person[address[zip_code]]" value="${personAddress.zipCode}">
</div>