<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>

<app:layout>
  <jsp:attribute name="headTitle">Persons</jsp:attribute>
  <jsp:attribute name="bodyTitle">Persons</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:queryForm queryProperties="${queryProperties}" selectedProperty="${selectedProperty}"/>
    </section>
    <section class="ui segment">
      <persons:table persons="${persons}" queryProperty="${selectedProperty}"/>
      <p><app:primaryButton url="/persons/add" text="Add person" icon="plus"/></p>
    </section>
  </jsp:body>
</app:layout>