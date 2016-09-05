<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.Person"%>
<%@attribute name="url" required="true" type="java.lang.String"%>

<form class="ui form" name="person" action="${url}" method="post">
  <persons:nameInputs personName="${person.name}"/>
  <persons:addressInputs personAddress="${person.address}"/>
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
  <button class="ui button" type="submit">Save</button>
</form>