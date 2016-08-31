<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<p><c:out value="${_notice}"/></p>

<table>
<thead>
  <th>Role</th>
  <th>Edit</th>
  <th>Delete</th>
</thead>
<c:forEach items="${roles}" var="role">
  <tr>
    <td><c:out value="${role.name}"/></td>
    <td><a href="/roles/${role.roleId}/edit">Edit</a></td>
    <td><a href="/roles/${role.roleId}/delete">Delete</a></td>
  </tr>
</c:forEach>
</table>

<form name="role" action="/roles" method="post">
  <label for="name">Name:</label>
  <input type="text" name="name"><br>
  <input type="submit" value="Create">
</form>

<form name="role" action="/roles/1" method="post">
  <label for="name">Name:</label>
  <input type="text" name="name"><br>
  <input type="submit" value="Update">
</form>

<p><a href="/roles/new">New Role</a></p>