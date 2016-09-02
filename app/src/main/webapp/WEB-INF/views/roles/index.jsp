<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table>
<thead>
  <th>Role</th>
  <th>Edit</th>
  <th>Delete</th>
</thead>
<tbody>
<c:forEach items="${roles}" var="role">
  <tr>
    <td><c:out value="${role.name}"/></td>
    <td><a href="/roles/${role.roleId}/edit">Edit</a></td>
    <td><a href="/roles/${role.roleId}/delete">Delete</a></td>
  </tr>
</c:forEach>
</tbody>
</table>

<p><a href="/roles/new">Add role</a></p>