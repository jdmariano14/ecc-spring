<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>

<app:layout>
  <jsp:attribute name="headTitle"><spring:message code="roles"/></jsp:attribute>
  <jsp:attribute name="bodyTitle"><spring:message code="roles"/></jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <roles:table roles="${roles}"/>
      <p><app:localizedPrimaryButton url="/roles/add" code="add_role" icon="plus"/></p>
    </section>
  </jsp:body>
</app:layout>