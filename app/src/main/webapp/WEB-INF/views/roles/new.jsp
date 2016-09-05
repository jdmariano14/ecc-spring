<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>

<app:layout>
  <jsp:attribute name="headTitle">Roles | New</jsp:attribute>
  <jsp:attribute name="bodyTitle">New Role</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <roles:form role="${role}" url="/roles" backUrl="/roles"/>
    </section>
  </jsp:body>
</app:layout>