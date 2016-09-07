<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="url" type="java.lang.String" required="true"%>
<%@attribute name="text" type="java.lang.String" required="true"%>
<%@attribute name="icon" type="java.lang.String" required="true"%>
<%@attribute name="iconOnly" type="java.lang.Boolean"%>

<a class="ui primary ${iconOnly ? 'icon button': 'button'}" href="${url}" title="${text}">
  <i class="${icon} icon"></i>
  <c:if test="${empty iconOnly or not iconOnly}">
    <c:out value="${text}"/>
  </c:if>
</a>