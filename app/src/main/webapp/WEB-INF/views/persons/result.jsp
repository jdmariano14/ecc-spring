<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>

<app:layout>
  <jsp:attribute name="headTitle">Persons | Query Result</jsp:attribute>
  <jsp:attribute name="bodyTitle">Person Query Result</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:queryForm queryProperties="${queryProperties}" selectedProperty="${selectedProperty}" backUrl="/persons"/>
    </section>
    <section class="ui segment">
      <persons:table persons="${persons}" queryProperty="${selectedProperty}"/>
    </section>
  </jsp:body>
</app:layout>