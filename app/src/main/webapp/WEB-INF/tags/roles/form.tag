<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="role" required="true" type="com.exist.ecc.person.core.dto.RoleDto"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" required="true" type="java.lang.String"%>

<form class="ui form" name="role" action="/roles/${role.roleId}" method="post">
  <div class="field">
    <label for="name">Name:</label>
    <input type="text" name="name" value="${role.name}"><br>
  </div>
  <p>
    <button class="ui primary button" type="submit">
      <i class="save icon"></i>
      Save
    </button>
    <c:if test="${not empty backUrl}">
      <app:backButton url="${backUrl}"/>
    </c:if>
  </p>
</form>