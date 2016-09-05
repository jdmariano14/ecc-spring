<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="roles" required="true" type="java.util.List"%>
<%@attribute name="url" required="true" type="java.lang.String"%>

<form class="ui form" name="person_role" action="${url}" method="post">
  <label for="person_role[role_id]">Role</label>
  <select name="person_role[role_id]">
    <option value="-1">Please select a role</option>
    <c:forEach items="${roles}" var="role">
      <option value="${role.roleId}"><c:out value="${role.name}"/></option>
    </c:forEach>
  </select><br>

  <button class="ui button" type="submit">Save</button>
</form>