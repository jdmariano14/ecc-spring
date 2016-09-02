<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="personName" required="true" type="com.exist.ecc.person.core.model.Name"%>

<fieldset>
  <legend>Name</legend>
  <label for="person[name[first_name]]">First name</label>
  <input type="text" name="person[name[first_name]]" value="${personName.firstName}">
  <label for="person[name[middle_name]]">Middle name</label>
  <input type="text" name="person[name[middle_name]]" value="${personName.middleName}">
  <label for="person[name[last_name]]">Last name</label>
  <input type="text" name="person[name[last_name]]" value="${personName.lastName}">
  <br>
  <label for="person[name[title]]">Title</label>
  <input type="text" name="person[name[title]]" value="${personName.title}">
  <label for="person[name[suffix]]">Suffix</label>
  <input type="text" name="person[name[suffix]]" value="${personName.suffix}">
</fieldset>