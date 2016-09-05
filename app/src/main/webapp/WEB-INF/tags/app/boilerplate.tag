<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="head" fragment="true"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title><jsp:invoke fragment="title"/></title>
    <app:importSemanticCss/>
    <link rel="stylesheet" href="/assets/stylesheets/app.css"/>
    <jsp:invoke fragment="head"/>
  </head>
  <body>
    <jsp:doBody/>
    <app:importJQuery/>
    <app:importSemanticJs/>
  </body>
</html>