<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons" %>

<app:layout>
  <jsp:attribute name="title">Person | Home</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:queryForm properties="${properties}"/>
    </section>
    <section class="ui segment">
      <persons:table persons="${persons}" queryProperty="${queryProperty}"/>
    </section>
    <section class="ui segment">
      <p><a href="/persons/new">Add person</a></p>
    </section>
  </jsp:body>
</app:layout>