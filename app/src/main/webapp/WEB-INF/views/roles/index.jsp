<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<table>
<thead>
  <th>Role</th>
  <th>Edit</th>
  <th>Delete</th>
</thead>
<c:forEach items="${roles}" var="role">
  <tr>
    <td><c:out value="${role.name}"/></td>
    <td><a href="#">Edit</a></td>
    <td><a href="#">Delete</a></td>
  </tr>
</c:forEach>
</table>

<p><a href="#">Add new role</a></p>