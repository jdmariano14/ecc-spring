<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.model.wrapper.RoleWrapper"%>

<tr>
  <td><c:out value="${role.name}"/></td>
  <td><a href="/roles/${role.roleId}/edit">Edit</a></td>
  <td><a href="/roles/${role.roleId}/delete">Delete</a></td>
</tr>
