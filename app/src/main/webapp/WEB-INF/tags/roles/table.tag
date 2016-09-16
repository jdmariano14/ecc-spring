<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="roles" required="true" type="java.util.List"%>

<table class="ui unstackable collapsing celled striped table">
  <thead>
    <tr>
      <th><spring:message code="role"/></th>
      <th class="center aligned"><spring:message code="edit"/></th>
      <th class="center aligned"><spring:message code="delete"/></th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${roles}" var="role">
    <roles:tableRow role="${role}"/>
  </c:forEach>
  </tbody>
</table>