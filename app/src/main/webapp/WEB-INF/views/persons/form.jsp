<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="person" action="/persons/${person.personId}" method="post">
  <fieldset>
    <legend>Name</legend>
    <label for="person[name[first_name]]">First name</label>
    <input type="text" name="person[name[first_name]]" value="${person.name.firstName}">
    <label for="person[name[middle_name]]">Middle name</label>
    <input type="text" name="person[name[middle_name]]" value="${person.name.middleName}">
    <label for="person[name[last_name]]">Last name</label>
    <input type="text" name="person[name[last_name]]" value="${person.name.lastName}">
    <br>
    <label for="person[name[title]]">Title</label>
    <input type="text" name="person[name[title]]" value="${person.name.title}">
    <label for="person[name[suffix]]">Suffix</label>
    <input type="text" name="person[name[suffix]]" value="${person.name.suffix}">
  </fieldset>

  <fieldset>
    <legend>Address</legend>
    <label for="person[address[street_address]]">Street address</label>
    <input type="text" name="person[address[street_address]]" value="${person.address.streetAddress}">
    <label for="person[address[barangay]]">Barangay</label>
    <input type="text" name="person[address[barangay]]" value="${person.address.barangay}">
    <label for="person[address[municipality]]">Municipality</label>
    <input type="text" name="person[address[municipality]]" value="${person.address.municipality}">
    <label for="person[address[zip_code]]">Zip code</label>
    <input type="text" name="person[address[zip_code]]" value="${person.address.zipCode}">
  </fieldset>

  <fieldset>
    <legend>Other info</legend>
    <label for="person[birth_date]">Birth date</label>
    <input type="date" name="person[birth_date]" value="${person.birthDate}">
    <br>
    <label for="person[date_hired]">Date hired</label>
    <input type="date" name="person[date_hired]" value="${person.dateHired}">
    <br>
    <label for="person[gwa]">GWA</label>
    <input type="number" name="person[gwa]" value="${person.gwa}" step=0.01>
    <br>
    <label for="person[employed]">Employed</label>
    <input type="radio" name="person[employed]" value="true" ${person.employed ? 'checked' : ''}> Yes
    <input type="radio" name="person[employed]" value="false" ${person.employed ? '' : 'checked'}> No<br>
  </fieldset>

  <input type="submit" value="Save">
</form>