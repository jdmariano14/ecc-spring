<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="headTitle" fragment="true"%>
<%@attribute name="bodyTitle" fragment="true"%>

<app:boilerplate>
  <jsp:attribute name="title">ECC | <jsp:invoke fragment="headTitle"/></jsp:attribute>
  <jsp:body>
    <app:navbar/>
    <section class="ui container">
      <div class="ui segments">
        <section class="ui segment">
          <h1><jsp:invoke fragment="bodyTitle"/></h1>
        </section>
        <jsp:doBody/>
      </div>
    </section>
  </jsp:body>
</app:boilerplate>