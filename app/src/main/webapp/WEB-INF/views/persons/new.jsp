<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons" %>

<app:layout>
  <jsp:attribute name="headTitle">Persons | New</jsp:attribute>
  <jsp:attribute name="bodyTitle">Persons - New</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:form person="${person}" url="/persons"/>
    </section>
    <section class="ui segment">
      <p><a href="/persons">Back</a></p>
    </section>
  </jsp:body>
</app:layout>