<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="person_role" action="/persons/${person.personId}/roles" method="post">
  <label for="person_role[role_id]">Role</label>
  <select name="person_role[role_id]">
    <option value="-1">Please select a role</option>
    <c:forEach items="${roles}" var="role">
      <option value="${role.roleId}"><c:out value="${role.name}"/></option>
    </c:forEach>
  </select><br>

  <input type="submit" value="Save">
</form>