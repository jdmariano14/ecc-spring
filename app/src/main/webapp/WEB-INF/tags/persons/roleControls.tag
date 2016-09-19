<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.dto.RoleDto"%>

<span>
(<a href="/personRoles/delete/?personId=${person.personId}&roleId=${role.roleId}">revoke</a>)
</span>