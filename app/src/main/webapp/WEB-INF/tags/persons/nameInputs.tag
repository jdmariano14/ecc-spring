<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@attribute name="personName" required="true" type="com.exist.ecc.person.core.dto.NameDto"%>

<h4 class="ui dividing header">Name</h4>
<div class="three fields">
  <div class="field">
    <label for="name-firstName">First</label>
    <form:input id="name-firstName" path="name.firstName"/>
  </div>
  <div class="field">
    <label for="name-middleName">Middle</label>
    <form:input id="name-firstName" path="name.middleName"/>
  </div>
  <div class="field">
    <label for="name-lastName">Last</label>
    <form:input id="name-lastName" path="name.lastName"/>
  </div>
</div>
<div class="two fields">
  <div class="four wide field">
    <label for="name-title">Title</label>
    <form:input id="name-title" path="name.title"/>
  </div>
  <div class="four wide field">
    <label for="name-suffix">Suffix</label>
    <form:input id="name-suffix" path="name.suffix"/>
  </div>
</div>