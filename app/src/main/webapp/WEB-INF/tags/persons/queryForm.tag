<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="properties" type="java.util.List"%>
<%@attribute name="queryProperty" type="java.lang.String"%>

<form class="ui form" name="person_query" action="/persons/query" method="post">
  <div class="field">
    <label for="person_query[property]">Query on</label>
    <select name="person_query[property]">
      <option value="">Please select a property</option>
      <c:forEach items="${properties}" var="property">
        <option value="${property}" ${property eq queryProperty ? 'selected' : ''}><c:out value="${property}"/></option>
      </c:forEach>
    </select>
  </div>
  <button class="ui primary button" type="submit">
    <i class="ui search icon"></i>
    Go
  </button>
</form>