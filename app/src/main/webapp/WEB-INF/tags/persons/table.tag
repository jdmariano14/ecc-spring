<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="persons" required="true" type="java.util.List"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<table class="ui unstackable collapsing celled striped table">
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
    <persons:tableRow person="${person}" queryProperty="${queryProperty}"/>
  </c:forEach>
  </tbody>
</table>