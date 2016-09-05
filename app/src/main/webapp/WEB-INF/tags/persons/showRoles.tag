<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>
<%@attribute name="roles" required="true" type="java.util.Set"%>

<section class="ui segment">
  <h2>Roles</h2>
  <ul>
  <c:forEach items="${person.roles}" var="role">
    <li>
      <c:out value="${role.name}"/>
      <persons:roleControls person="${person}" role="${role}"/>
    </li>
  </c:forEach>
  </ul>
  <p><a class="ui primary button" href="/persons/${person.personId}/roles/new">
    <i class="plus icon"></i>
    Grant role
  </a></p>
</section>