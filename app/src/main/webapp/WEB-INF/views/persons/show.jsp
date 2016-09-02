<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons" %>

<app:layout>
  <jsp:attribute name="headTitle">Persons | Show</jsp:attribute>
  <jsp:attribute name="bodyTitle">Persons - Show</jsp:attribute>
  <jsp:body>
    <persons:show person="${person}"/>
  </jsp:body>
</app:layout>