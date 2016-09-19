<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="contactTypes" type="java.util.List"%>
<%@attribute name="contact" required="true" type="com.exist.ecc.person.core.dto.ContactDto"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form class="ui form" name="contact" action="${url}" method="post">
  <c:if test="${contact.contactId le 0}">
    <div class="four wide field">
      <label for="contactType">Type</label>
      <select name="contactType">
        <option value="">Please select a type</option>
        <c:forEach items="${contactTypes}" var="contactType">
          <option value="${contactType}"><c:out value="${contactType}"/></option>
        </c:forEach>
      </select>
    </div>
  </c:if>
  <div class="hidden field">
    <input type="hidden" name="personId" value="${contact.personId}"><br>
  </div>
  <div class="field">
    <label for="value">Value</label>
    <input type="text" name="value" value="${contact.value}"><br>
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