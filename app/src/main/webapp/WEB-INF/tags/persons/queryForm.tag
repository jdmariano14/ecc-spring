<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="queryProperties" type="java.util.List"%>
<%@attribute name="selectedProperty" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form class="ui form" name="person_query" action="/persons/query" method="post">
  <div class="field">
    <label for="queryProperty">Query on</label>
    <select name="queryProperty">
      <option value="">Please select a property</option>
      <c:forEach items="${queryProperties}" var="property">
        <option value="${property}" ${property eq selectedProperty ? 'selected' : ''}><c:out value="${property}"/></option>
      </c:forEach>
    </select>
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