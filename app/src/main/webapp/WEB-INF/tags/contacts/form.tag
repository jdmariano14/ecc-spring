<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="contactTypes" type="java.util.Set"%>
<%@attribute name="contact" required="true" type="com.exist.ecc.person.core.model.Contact"%>
<%@attribute name="url" required="true" type="java.lang.String"%>

<form name="contact" action="${url}" method="post">
  <c:if test="${contact.contactId le 0}">
    <label for="contact[type]">Type</label>
    <select name="contact[type]">
      <option value="">Please select a type</option>
      <c:forEach items="${contactTypes}" var="contactType">
        <option value="${contactType}"><c:out value="${contactType}"/></option>
      </c:forEach>
    </select><br>
  </c:if>

  <label for="contact[info]">Value</label>
  <input type="text" name="contact[info]" value="${contact.info}"><br>
  <input type="submit" value="Save">
</form>