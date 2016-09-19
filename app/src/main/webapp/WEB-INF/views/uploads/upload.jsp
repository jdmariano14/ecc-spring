<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="uploads" tagdir="/WEB-INF/tags/uploads"%>

<app:layout>
  <jsp:attribute name="headTitle">Upload <c:out value="${uploadType}"/></jsp:attribute>
  <jsp:attribute name="bodyTitle">Upload <c:out value="${uploadType}"/></jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <uploads:form uploadType="${uploadType}" url="/uploads/process" backUrl="/"/>
    </section>
  </jsp:body>
</app:layout>