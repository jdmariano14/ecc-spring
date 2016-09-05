<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.Person"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form class="ui form" name="person" action="${url}" method="post">
  <persons:nameInputs personName="${person.name}"/>
  <persons:addressInputs personAddress="${person.address}"/>

  <h4 class="ui dividing header">Other information</h4>
  <div class="three fields">
    <div class="field">
      <label for="person[birth_date]">Birth date</label>
      <input type="date" name="person[birth_date]" value="${person.birthDate}">
    </div>
    <div class="field">
      <label for="person[date_hired]">Date hired</label>
      <input type="date" name="person[date_hired]" value="${person.dateHired}">
    </div>
    <div class="field">
      <label for="person[gwa]">GWA</label>
      <input type="number" name="person[gwa]" value="${person.gwa}" step="0.01">
    </div>
  </div>
  <div class="inline fields">
    <label for="person[employed]">Employed</label>
    <div class="field">
      <input type="radio" name="person[employed]" value="true" ${person.employed ? 'checked' : ''}>
      <label>Yes</label>
    </div>
    <div class="field">
      <input type="radio" name="person[employed]" value="false" ${person.employed ? '' : 'checked'}>
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
</form>