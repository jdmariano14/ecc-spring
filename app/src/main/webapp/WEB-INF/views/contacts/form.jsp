<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="action" scope="session" value="${empty contact.contactId ? '/persons/' += person.personId += '/contacts' : '/contacts/' += contact.contactId}"/>

<form name="contact" action="${action}" method="post">
  <c:if test="${empty contact.contactId}">
    <label for="contact[type]">Type</label>
    <select name="contact[type]">
      <option value="">Please select a type</option>
      <c:forEach items="${types}" var="type">
        <option value="${type}"><c:out value="${type}"/></option>
      </c:forEach>
    </select><br>
  </c:if>

  <label for="contact[info]">Value</label>
  <input type="text" name="contact[info]" value="${contact.info}"><br>
  <input type="submit" value="Save">
</form>