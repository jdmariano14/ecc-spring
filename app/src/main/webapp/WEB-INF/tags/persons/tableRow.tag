<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<tr>
  <td><a href="/persons/${person.personId}"><c:out value="${person.name.formalShortName}"/></a></td>
  <c:choose>
    <c:when test="${queryProperty eq 'Date hired'}">
      <td><c:out value="${person.dateHired}"/></td>
    </c:when>
    <c:when test="${queryProperty eq 'GWA'}">
      <td><c:out value="${person.gwa}"/></td>
    </c:when>
  </c:choose>
  <td><a class="ui primary icon button" title="Edit" href="/persons/${person.personId}/edit">
    <i class="ui write icon">
  </a></td>
  <td><a class="ui primary icon button" title="Delete" href="/persons/${person.personId}/delete">
    <i class="ui trash outline icon">
  </a></td>
</tr>