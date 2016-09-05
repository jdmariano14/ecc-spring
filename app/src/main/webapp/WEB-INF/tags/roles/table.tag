<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>
<%@attribute name="roles" required="true" type="java.util.List"%>

<table class="ui unstackable collapsing celled striped table">
  <thead>
    <th>Role</th>
    <th>Edit</th>
    <th>Delete</th>
  </thead>
  <tbody>
  <c:forEach items="${roles}" var="role">
    <roles:tableRow role="${role}"/>
  </c:forEach>
  </tbody>
</table>