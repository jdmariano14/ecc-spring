<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="notice" type="java.lang.String"%>
<%@attribute name="error" type="java.lang.String"%>

<c:if test="${not empty notice}">
  <div class="ui success message">
    <div class="header"><c:out value="${notice}"/></div>
  </div>
</c:if>

<c:if test="${not empty error}">
  <div class="ui error message">
    <div class="header"><c:out value="${error}"/></div>
  </div>
</c:if>