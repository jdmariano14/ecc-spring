<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.model.wrapper.RoleWrapper"%>

<tr>
  <td><c:out value="${role.name}"/></td>
  <td class="center aligned">
    <a class="ui primary icon button" title="Edit" href="/roles/${role.roleId}/edit">
      <i class="ui write icon"></i>
    </a>
  </td>
  <td class="center aligned">
    <a class="ui primary icon button" title="Delete" href="/roles/${role.roleId}/delete">
      <i class="ui trash outline icon"></i>
    </a>
  </td>
</tr>
