<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.dto.RoleDto"%>

<tr>
  <td><c:out value="${role.name}"/></td>
  <td class="center aligned">
    <app:primaryButton url="/roles/edit?id=${role.roleId}" text="Edit ${role.name}" icon="write" iconOnly="${true}"/>
  </td>
  <td class="center aligned">
    <app:primaryButton url="/roles/${role.roleId}/delete" text="Delete ${role.name}" icon="trash outline" iconOnly="${true}"/>
  </td>
</tr>
