<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>

<app:layout>
  <jsp:attribute name="headTitle"><c:out value="${person.name.shortName}"/></jsp:attribute>
  <jsp:attribute name="bodyTitle"><c:out value="${person.name.fullName}"/></jsp:attribute>
  <jsp:body>
    <persons:show person="${person}"/>
  </jsp:body>
</app:layout>