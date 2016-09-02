<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

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