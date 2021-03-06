<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="persons" required="true" type="java.util.List"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<table class="ui unstackable collapsing celled striped table">
  <thead>
    <tr>
      <th>Person</th>
      <c:choose>
        <c:when test="${selectedProperty eq 'Date hired'}">
          <th>Date hired</th>
        </c:when>
        <c:when test="${selectedProperty eq 'GWA'}">
          <th>GWA</th>
        </c:when>
      </c:choose>
      <th class="center aligned">Show</th>
      <th class="center aligned">Edit</th>
      <th class="center aligned">Delete</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${persons}" var="person">
    <persons:tableRow person="${person}" queryProperty="${queryProperty}"/>
  </c:forEach>
  </tbody>
</table>