<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>

<app:layout>
  <jsp:attribute name="headTitle">Persons</jsp:attribute>
  <jsp:attribute name="bodyTitle">Persons</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:queryForm properties="${properties}" queryProperty="${queryProperty}"/>
    </section>
    <section class="ui segment">
      <persons:table persons="${persons}" queryProperty="${queryProperty}"/>
      <p><app:primaryButton url="/persons/new" text="Add person" icon="plus"/></p>
    </section>
  </jsp:body>
</app:layout>