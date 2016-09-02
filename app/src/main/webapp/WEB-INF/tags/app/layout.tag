<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="title" fragment="true"%>

<!DOCTYPE html>
<html>
  <head>
      <meta charset="utf-8" />
      <title>ECC | <jsp:invoke fragment="title"/></title>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.css"/>
      <link rel="stylesheet" href="/WEB-INF/assets/stylesheets/app.css"/>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/1.11.8/semantic.min.js"></script>
  </head>
  <body>
    <app:navbar/>
    <section class="ui container">
      <div class="ui segments">
        <section class="ui segment">
          <h1><jsp:invoke fragment="title"/></h1>
        </section>
        <app:flash notice="${_notice}" error="${_error}"/>
        <jsp:doBody/>
      </div>
    </section>
  </body>
</html>