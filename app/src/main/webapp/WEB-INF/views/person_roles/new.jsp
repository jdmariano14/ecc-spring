<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="personRoles" tagdir="/WEB-INF/tags/person_roles"%>

<app:layout>
  <jsp:attribute name="headTitle">Person Roles | New</jsp:attribute>
  <jsp:attribute name="bodyTitle">Person Roles - New</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <personRoles:form roles="${roles}" url="/persons/${person.personId}/roles"/>
    </section>
  </jsp:body>
</app:layout>