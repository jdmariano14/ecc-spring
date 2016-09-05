<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="contacts" tagdir="/WEB-INF/tags/contacts"%>

<app:layout>
  <jsp:attribute name="headTitle"><c:out value="${person.name.shortName}"/> | Contacts | Edit</jsp:attribute>
  <jsp:attribute name="bodyTitle">Edit Contact</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <contacts:form contact="${contact}" contactTypes="${contactTypes}" url="/contacts/${contact.contactId}"/>
    </section>
  </jsp:body>
</app:layout>