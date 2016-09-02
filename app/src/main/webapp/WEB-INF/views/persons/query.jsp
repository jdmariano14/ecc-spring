<%@page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app" %>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons" %>

<app:layout>
  <jsp:attribute name="title">Person | Query</jsp:attribute>
  <jsp:body>
    <section class="ui segment">
      <persons:resultForm property="${property}"
                          minString="${minString}"
                          maxString="${maxString}"
                          likeString="${likeString}"
                          minDate="${minDate}"
                          maxDate="${maxDate}"
                          minBigDecimal="${minBigDecimal}"            
                          maxBigDecimal="${maxBigDecimal}"
                          order="${order}"/>
    </section>
    <section class="ui segment">
      <p><a href="/persons">Back</a></p>
    </section>
  </jsp:body>
</app:layout>