<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>

<app:layout>
  <jsp:attribute name="headTitle">Persons | New</jsp:attribute>
  <jsp:attribute name="bodyTitle">New Person</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:form person="${person}" url="/persons" backUrl="/persons"/>
    </section>
  </jsp:body>
</app:layout>