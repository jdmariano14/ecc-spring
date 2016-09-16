<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>
<%@attribute name="roles" required="true" type="java.util.List"%>

<section class="ui segment">
  <h2>Roles</h2>
  <ul>
  <c:forEach items="${roles}" var="role">
    <li>
      <c:out value="${role.name}"/>
      <persons:roleControls person="${person}" role="${role}"/>
    </li>
  </c:forEach>
  </ul>
  <p><app:primaryButton url="/persons/${person.personId}/roles/new" text="Grant role" icon="plus"/></p>
</section>