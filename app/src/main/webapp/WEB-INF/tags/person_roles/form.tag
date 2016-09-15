<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="roles" required="true" type="java.util.List"%>
<%@attribute name="url" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" required="true" type="java.lang.String"%>

<form class="ui form" name="person_role" action="${url}" method="post">
  <div class="field">
    <label for="roleId">Role</label>
    <select name="roleId">
      <option value="-1">Please select a role</option>
      <c:forEach items="${roles}" var="role">
        <option value="${role.roleId}"><c:out value="${role.name}"/></option>
      </c:forEach>
    </select>
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