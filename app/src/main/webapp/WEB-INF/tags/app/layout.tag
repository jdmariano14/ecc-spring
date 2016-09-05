<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="headTitle" fragment="true"%>
<%@attribute name="bodyTitle" fragment="true"%>
<%@attribute name="_notice" type="java.lang.String"%>
<%@attribute name="_error" type="java.lang.String"%>

<!DOCTYPE html>
<html>
  <head>
      <meta charset="utf-8" />
      <title>ECC | <jsp:invoke fragment="headTitle"/></title>
  </head>
  <body>
    <app:navbar/>
    <section class="ui container">
      <div class="ui segments">
        <section class="ui segment">
          <h1><jsp:invoke fragment="bodyTitle"/></h1>
        </section>
        <app:flash notice="${_notice}" error="${_error}"/>
        <jsp:doBody/>
      </div>
    </section>
  </body>
</html>