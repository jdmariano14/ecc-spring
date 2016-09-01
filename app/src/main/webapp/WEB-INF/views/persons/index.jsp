<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p><c:out value="${_notice}"/></p>
<p><c:out value="${_error}"/></p>

<form name="person_query" action="/persons/query" method="post">
  <label for="person_query[property]">Query on</label>
  <select name="person_query[property]">
    <option value="">Please select a property</option>
    <c:forEach items="${properties}" var="property">
      <option value="${property}"><c:out value="${property}"/></option>
    </c:forEach>
  </select><br>

  <input type="submit" value="Go">
</form>

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