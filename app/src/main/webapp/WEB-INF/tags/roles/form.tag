<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.model.Role"%>
<%@attribute name="url" required="true" type="java.lang.String"%>

<form name="role" action="/roles/${role.roleId}" method="post">
  <label for="role[name]">Name:</label>
  <input type="text" name="role[name]" value="${role.name}"><br>
  <input type="submit" value="Save">
</form>