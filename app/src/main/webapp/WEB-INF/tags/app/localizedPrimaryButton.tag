<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@attribute name="url" type="java.lang.String" required="true"%>
<%@attribute name="code" type="java.lang.String" required="true"%>
<%@attribute name="icon" type="java.lang.String" required="true"%>
<%@attribute name="iconOnly" type="java.lang.Boolean"%>

<a class="ui primary ${iconOnly ? 'icon button': 'button'}" href="${url}" title="${text}">
  <i class="${icon} icon"></i>
  <c:if test="${empty iconOnly or not iconOnly}">
    <spring:message code="${code}"/>
  </c:if>
</a>