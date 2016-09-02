<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="persons" required="true" type="java.util.List"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<table class="ui table">
  <thead>
    <th>Person</th>
    <c:choose>
      <c:when test="${queryProperty eq 'Date hired'}">
        <th>Date hired</th>
      </c:when>
      <c:when test="${queryProperty eq 'GWA'}">
        <th>GWA</th>
      </c:when>
    </c:choose>
    <th>Edit</th>
    <th>Delete</th>
  </thead>
  <tbody>
  <c:forEach items="${persons}" var="person">
    <tr>
      <td><a href="/persons/${person.personId}"><c:out value="${person.name.shortName}"/></a></td>
      <c:choose>
        <c:when test="${queryProperty eq 'Date hired'}">
          <td><c:out value="${person.dateHired}"/></td>
        </c:when>
        <c:when test="${queryProperty eq 'GWA'}">
          <td><c:out value="${person.gwa}"/></td>
        </c:when>
      </c:choose>
      <td><a href="/persons/${person.personId}/edit">Edit</a></td>
      <td><a href="/persons/${person.personId}/delete">Delete</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>