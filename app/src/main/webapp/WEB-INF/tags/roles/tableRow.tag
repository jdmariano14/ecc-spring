<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.model.wrapper.RoleWrapper"%>

<tr>
  <td><c:out value="${role.name}"/></td>
  <td><a class="ui primary icon button" title="Edit" href="/roles/${role.roleId}/edit">
    <i class="ui edit icon">
  </a></td>
  <td><a class="ui primary icon button" title="Delete" href="/roles/${role.roleId}/delete">
    <i class="ui trash outline icon">
  </a></td>
</tr>
