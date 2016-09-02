<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>

<app:layout>
  <jsp:attribute name="headTitle">Roles | Home</jsp:attribute>
  <jsp:attribute name="bodyTitle">Roles - Home</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <roles:table roles="${roles}"/>
    </section>
    <section class="ui segment">
      <p><a href="/roles/new">Add role</a></p>
    </section>
  </jsp:body>
</app:layout>