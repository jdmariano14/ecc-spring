<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="queryProperty" type="java.lang.String"%>
<%@attribute name="minString" type="java.lang.String"%>
<%@attribute name="maxString" type="java.lang.String"%>
<%@attribute name="likeString" type="java.lang.String"%>
<%@attribute name="minDate" type="java.lang.String"%>
<%@attribute name="maxDate" type="java.lang.String"%>
<%@attribute name="minBigDecimal" type="java.lang.String"%>
<%@attribute name="maxBigDecimal" type="java.lang.String"%>
<%@attribute name="order" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form class="ui form" name="person_result" action="/persons/result" method="post">
  <h4 class="ui dividing header"><c:out value="${queryProperty}"/></h4>
  <input type="hidden" name="queryProperty" value="${queryProperty}">
  <c:if test="${not empty minString or not empty maxString or not empty likeString}">
    <div class="three fields">
      <div class="field">
        <label for="minString">From</label>
        <input type="text" name="minString" placeholder="${minString}">
      </div>
      <div class="field">
        <label for="maxString">To</label>
        <input type="text" name="maxString" placeholder="${maxString}">
      </div>
      <div class="field">
        <label for="likeString">Like</label>
        <input type="text" name="likeString" placeholder="${likeString}">
      </div>
    </div>
  </c:if>
  <c:if test="${not empty minDate or not empty maxDate}">
    <div class="two fields">
      <div class="field">
        <label for="minDate">From</label>
        <input type="date" name="minDate" placeholder="${minDate}">
      </div>
      <div class="field">
        <label for="maxDate">To</label>
        <input type="date" name="maxDate" placeholder="${maxDate}">
      </div>
    </div>
  </c:if>
  <c:if test="${not empty minBigDecimal or not empty maxBigDecimal}">
    <div class="two fields">
      <div class="four wide field">
        <label for="minBigDecimal">High</label>
        <input type="number" name="minBigDecimal" placeholder="${minBigDecimal}" step="0.01">
      </div>
      <div class="four wide field">
        <label for="maxBigDecimal">Low</label>
        <input type="number" name="maxBigDecimal" placeholder="${maxBigDecimal}" step="0.01">
      </div>
    </div>
  </c:if>
  <div class="inline fields">
    <label for="order">Order</label>
    <div class="field">
      <input type="radio" name="order" value="asc" checked>
      <label title="Ascending order">asc</label>
    </div>
    <div class="field">
      <input type="radio" name="order" value="desc">
      <label title="Descending order">desc</label>
    </div>
  </div>
  <p>
    <button class="ui primary button" type="submit">
      <i class="search icon"></i>
      Go
    </button>
    <c:if test="${not empty backUrl}">
      <app:backButton url="${backUrl}"/>
    </c:if>
  </p>
</form>