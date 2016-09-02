<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons" %>

<app:layout>
  <jsp:attribute name="title">Persons</jsp:attribute>
  <jsp:body>
    <persons:queryForm properties="${properties}"/>
    <persons:table persons="${persons}" queryProperty="${queryProperty}"/>
    <p><a href="/persons/new">Add person</a></p>
  </jsp:body>
</app:layout>