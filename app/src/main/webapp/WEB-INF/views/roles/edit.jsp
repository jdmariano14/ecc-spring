<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles" %>

<app:layout>
  <jsp:attribute name="headTitle">Roles | Edit</jsp:attribute>
  <jsp:attribute name="bodyTitle">Roles - Edit</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <roles:form role="${role}" url="/roles/${role.roleId}"/>
    </section>
    <section class="ui segment">
      <p><a href="/roles">Back</a></p>
    </section>
  </jsp:body>
</app:layout>