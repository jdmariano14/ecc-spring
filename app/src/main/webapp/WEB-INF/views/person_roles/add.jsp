<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="personRoles" tagdir="/WEB-INF/tags/person_roles"%>

<app:layout>
  <jsp:attribute name="headTitle"><c:out value="${person.name.shortName}"/> | Person Roles | Add</jsp:attribute>
  <jsp:attribute name="bodyTitle">Add Person Role</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <personRoles:form person="${person}" roles="${roles}" url="/personroles/create" backUrl="/persons/show?=${person.personId}"/>
    </section>
  </jsp:body>
</app:layout>