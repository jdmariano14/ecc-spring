<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="notice" type="java.lang.String"%>
<%@attribute name="error" type="java.lang.String"%>

<c:if test="${not empty notice or not empty error}">
  <section class="ui segment">
    <p class="flash">
      <span class="notice"><c:out value="${notice}"/></span>
      <span class="error"><c:out value="${error}"/></span>
    </p>
  </section>
</c:if>