<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@attribute name="personAddress" required="true" type="com.exist.ecc.person.core.dto.AddressDto"%>

<h4 class="ui dividing header">Address</h4>
<div class="field">
  <label for="address-streetAddress">Street</label>
  <form:input id="address-streetAddress" path="address.streetAddress"/>
</div>
<div class="two fields">
  <div class="field">
    <label for="address-barangay">Barangay</label>
    <form:input id="address-barangay" path="address.barangay"/>
  </div>
  <div class="field">
    <label for="address-municipality">Municipality</label>
    <form:input id="address-municipality" path="address.municipality"/>
  </div>
</div>
<div class="three wide field">
  <label for="address-zipCode">Zip</label>
  <form:input id="address-zipCode" path="address.zipCode"/>
</div>