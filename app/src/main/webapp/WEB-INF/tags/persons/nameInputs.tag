<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="personName" required="true" type="com.exist.ecc.person.core.model.Name"%>

<h4 class="ui dividing header">Name</h4>
<div class="three fields">
  <div class="field">
    <label for="person[name[first_name]]">First</label>
    <input type="text" name="person[name[first_name]]" value="${personName.firstName}">
  </div>
  <div class="field">
    <label for="person[name[middle_name]]">Middle</label>
    <input type="text" name="person[name[middle_name]]" value="${personName.middleName}">
  </div>
  <div class="field">
    <label for="person[name[last_name]]">Last</label>
    <input type="text" name="person[name[last_name]]" value="${personName.lastName}">
  </div>
</div>
<div class="two fields">
  <div class="four wide field">
    <label for="person[name[title]]">Title</label>
    <input type="text" name="person[name[title]]" value="${personName.title}">
  </div>
  <div class="four wide field">
    <label for="person[name[suffix]]">Suffix</label>
    <input type="text" name="person[name[suffix]]" value="${personName.suffix}">
  </div>
</div>