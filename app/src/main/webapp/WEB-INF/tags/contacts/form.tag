<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="contactTypes" type="java.util.Set"%>
<%@attribute name="contact" required="true" type="com.exist.ecc.person.core.model.Contact"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>


<form class="ui form" name="contact" action="${url}" method="post">
  <c:if test="${contact.contactId le 0}">
    <div class="four wide field">
      <label for="contact[type]">Type</label>
      <select name="contact[type]">
        <option value="">Please select a type</option>
        <c:forEach items="${contactTypes}" var="contactType">
          <option value="${contactType}"><c:out value="${contactType}"/></option>
        </c:forEach>
      </select>
    </div>
  </c:if>
  <div class="field">
    <label for="contact[info]">Value</label>
    <input type="text" name="contact[info]" value="${contact.info}"><br>
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