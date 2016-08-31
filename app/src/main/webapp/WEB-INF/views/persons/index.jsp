<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<p><c:out value="${_notice}"/></p>
<p><c:out value="${_error}"/></p>

<table>
<thead>
  <th>Person</th>
  <th>Edit</th>
  <th>Delete</th>
</thead>
<tbody>
<c:forEach items="${persons}" var="person">
  <tr>
    <td><a href="/persons/${person.personId}"><c:out value="${person.name.shortName}"/></a></td>
    <td><a href="/persons/${person.personId}/edit">Edit</a></td>
    <td><a href="/persons/${person.personId}/delete">Delete</a></td>
  </tr>
</c:forEach>
</tbody>
</table>

<p><a href="/persons/new">Add person</a></p>