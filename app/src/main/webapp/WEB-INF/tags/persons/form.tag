<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.Person"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form:form modelAttribute="person" action="${url}" method="post" cssClass="ui form">
  <persons:nameInputs personName="${person.name}"/>
  <persons:addressInputs personAddress="${person.address}"/>

  <h4 class="ui dividing header">Other information</h4>
  <div class="three fields">
    <div class="field">
      <label for="birthDate">Birth date</label>
      <input type="date" name="birthDate" value="${person.birthDate}">
    </div>
    <div class="field">
      <label for="dateHired">Date hired</label>
      <input type="date" name="dateHired" value="${person.dateHired}">
    </div>
    <div class="field">
      <label for="gwa">GWA</label>
      <input type="number" name="gwa" value="${person.gwa}" step="0.01">
    </div>
  </div>
  <div class="inline fields">
    <label for="employed">Employed</label>
    <div class="field">
      <input type="radio" name="employed" value="true" ${person.employed ? 'checked' : ''}>
      <label>Yes</label>
    </div>
    <div class="field">
      <input type="radio" name="employed" value="false" ${person.employed ? '' : 'checked'}>
      <label>No</label>
    </div>
  </div>
  <p>
    <button class="ui primary button" type="submit">
      <i class="save icon"></i>
      Save
    </button>
    <c:if test="${not empty backUrl}">
      <app:backButton url="${backUrl}"/>
    </c:if>
  </p>
</form:form>