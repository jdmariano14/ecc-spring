<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<nav class="ui stackable navbar menu">
  <div class="header item">ECC Spring Activity</div>
  <a class="item" href="/"><spring:message code="home"/></a>
  <a class="item" href="/persons"><spring:message code="persons"/></a>
  <a class="item" href="/roles"><spring:message code="roles"/></a>
  <div class="ui simple dropdown item">
    <spring:message code="upload"/>
    <i class="dropdown icon"></i>
    <div class="menu">
      <a class="item" href="/uploads/?uploadType=Persons"><spring:message code="persons"/></a>
      <a class="item" href="/uploads/?uploadType=Roles"><spring:message code="roles"/></a>
      <a class="item" href="/uploads/?uploadType=Contacts"><spring:message code="contacts"/></a>
      <a class="item" href="/uploads/?uploadType=Person Roles"><spring:message code="person_roles"/></a>
    </div>
  </div>
</nav>