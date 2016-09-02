<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form name="role" action="/roles/${role.roleId}" method="post">
  <label for="role[name]">Name:</label>
  <input type="text" name="role[name]" value="${role.name}"><br>
  <input type="submit" value="Save">
</form>