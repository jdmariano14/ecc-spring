<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<tr>
  <td><c:out value="${person.name.formalShortName}"/></td>
  <c:choose>
    <c:when test="${queryProperty eq 'Date hired'}">
      <td><c:out value="${person.dateHired}"/></td>
    </c:when>
    <c:when test="${queryProperty eq 'GWA'}">
      <td><c:out value="${person.gwa}"/></td>
    </c:when>
  </c:choose>
  <td class="center aligned">
    <app:primaryButton url="/persons/${person.personId}" text="Show ${person.name.shortName}" icon="open folder" iconOnly="true"/>
  </td>
  <td class="center aligned">
    <app:primaryButton url="/persons/edit?id=${person.personId}" text="Edit ${person.name.shortName}" icon="write" iconOnly="true"/>
  </td>
  <td class="center aligned">
    <app:primaryButton url="/persons/${person.personId}/delete" text="Delete ${person.name.shortName}" icon="trash outline" iconOnly="true"/>
  </td>
</tr>