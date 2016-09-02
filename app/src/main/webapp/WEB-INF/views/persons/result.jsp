<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty property}">
  <p>
    <strong>Property:</strong>
    <span><c:out value="${property}"/></span>
  </p>
</c:if>
<c:if test="${not empty min_string}">
  <p>
    <strong>Min string:</strong>
    <span><c:out value="${min_string}"/></span>
  </p>
</c:if>
<c:if test="${not empty max_string}">
  <p>
    <strong>Max string:</strong>
    <span><c:out value="${max_string}"/></span>
  </p>
</c:if>
<c:if test="${not empty like_string}">
  <p>
    <strong>Like string:</strong>
    <span><c:out value="${like_string}"/></span>
  </p>
</c:if>
<c:if test="${not empty min_date}">
  <p>
    <strong>Min date:</strong>
    <span><c:out value="${min_date}"/></span>
  </p>
</c:if>
<c:if test="${not empty max_date}">
  <p>
    <strong>Max date:</strong>
    <span><c:out value="${max_date}"/></span>
  </p>
</c:if>
<c:if test="${not empty min_big_decimal}">
  <p>
    <strong>Min big dec:</strong>
    <span><c:out value="${min_big_decimal}"/></span>
  </p>
</c:if>
<c:if test="${not empty max_big_decimal}">
  <p>
    <strong>Max big dec:</strong>
    <span><c:out value="${max_big_decimal}"/></span>
  </p>
</c:if>
<c:if test="${not empty order}">
  <p>
    <strong>Order:</strong>
    <span><c:out value="${order}"/></span>
  </p>
</c:if>

<p><a href="/persons">Back</a></p>