<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>

<app:boilerplate>
  <jsp:attribute name="title">ECC Servlet Activity</jsp:attribute>
  <jsp:body>
    <div class="ui middle aligned center aligned full height ecc home grid">
      <div class="column">
        <div class="ui container">
          <h1 class="ui header">ECC Servlet Activity</h1>
          <nav class="ui two item main menu">
            <a class="item" href="/persons">Persons</a>
            <a class="item" href="/roles">Roles</a>
          </nav>
          <app:flash notice="${_notice.message}" error="${_error.message}"/>
        </div>
      </div>
    </div>
  </jsp:body>
</app:boilerplate>