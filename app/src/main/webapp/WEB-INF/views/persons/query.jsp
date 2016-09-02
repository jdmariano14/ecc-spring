<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="person_result" action="/persons/result" method="post">
  <fieldset>
    <legend><c:out value="${property}"/></legend>
    <c:if test="${not empty minString}">
      <label for="person_result[min_string]">From</label>
      <input type="text" name="person_result[min_string]" placeholder="${minString}">
      <br>
    </c:if>
    <c:if test="${not empty maxString}">
      <label for="person_result[max_string]">To</label>
      <input type="text" name="person_result[max_string]" placeholder="${maxString}">
      <br>
    </c:if>
    <c:if test="${not empty likeString}">
      <label for="person_result[like_string]">Like</label>
      <input type="text" name="person_result[like_string]" placeholder="${likeString}">
      <br>
    </c:if>
    <c:if test="${not empty minDate}">
      <label for="person_result[min_date]">From</label>
      <input type="date" name="person[min_date]" placeholder="${minDate}">
      <br>
    </c:if>
    <c:if test="${not empty maxDate}">
      <label for="person_result[max_date]">To</label>
      <input type="date" name="person[max_date]" placeholder="${maxDate}">
      <br>
    </c:if>
    <c:if test="${not empty minBigDecimal}">
      <label for="person_result[min_big_decimal]">High</label>
      <input type="number" name="person_result[min_big_decimal]" placeholder="${minBigDecimal}" step=0.01>
      <br>
    </c:if>
    <c:if test="${not empty maxBigDecimal}">
      <label for="person_result[max_big_decimal]">Low</label>
      <input type="number" name="person_result[max_big_decimal]" placeholder="${maxBigDecimal}" step=0.01>
      <br>
    </c:if>
    <label for="person_result[order]">Order</label>
    <input type="radio" name="person_result[order]" value="asc" checked> asc
    <input type="radio" name="person_result[order]" value="desc"> desc
    <br>
    <input type="submit" value="Go">
  </fieldset>
</form>

<p><a href="/persons">Back</a></p>