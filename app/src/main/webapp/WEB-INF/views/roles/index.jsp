<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="roles" tagdir="/WEB-INF/tags/roles"%>

<app:layout>
  <jsp:attribute name="headTitle">Roles</jsp:attribute>
  <jsp:attribute name="bodyTitle">Roles</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <roles:table roles="${roles}"/>
      <p>
        <a class="ui primary button" href="/roles/new">
          <i class="plus icon"></i>
          Add role
        </a>
      </p>
    </section>
  </jsp:body>
</app:layout>