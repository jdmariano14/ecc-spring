<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.dto.RoleDto"%>

<span>
(<a href="/persons/${person.personId}/roles/${role.roleId}/delete">revoke</a>)
</span>