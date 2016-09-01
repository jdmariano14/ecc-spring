<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="contact" action="/contacts/${contact.contactId}" method="post">
  <label for="contact[info]">Value</label>
  <input type="text" name="contact[info]" value="${contact.info}"><br>
  <input type="submit" value="Save">
</form>