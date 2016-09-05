<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="headTitle" fragment="true"%>
<%@attribute name="bodyTitle" fragment="true"%>
<%@attribute name="_notice" type="com.exist.ecc.person.app.flash.Flash"%>
<%@attribute name="_error" type="com.exist.ecc.person.app.flash.Flash"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>ECC | <jsp:invoke fragment="headTitle"/></title>
    <app:importSemanticCss/>
    <link rel="stylesheet" href="/assets/stylesheets/app.css"/>
  </head>
  <body>
    <app:navbar/>
    <section class="ui container">
      <div class="ui segments">
        <section class="ui segment">
          <h1><jsp:invoke fragment="bodyTitle"/></h1>
          <app:flash notice="${_notice.message}" error="${_error.message}"/>
        </section>
        <jsp:doBody/>
      </div>
    </section>
    <app:importJQuery/>
    <app:importSemanticJs/>
  </body>
</html>