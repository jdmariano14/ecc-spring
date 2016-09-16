<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="uploadType" required="true" type="java.lang.String"%>
<%@attribute name="backUrl" type="java.lang.String"%>

<form class="ui form" method="post" enctype="multipart/form-data" action="/uploads">
  <div class="hidden field">
    <input type="hidden" name="uploadType" value="${uploadType}">
  </div>
  <div class="eight wide field">
    <label for="file" title="A CSV file">File</label>
    <input type="file" name="file">
  </div>
  <button class="ui primary button" type="submit">
    <i class="upload icon"></i>
    Upload
  </button>
  <c:if test="${not empty backUrl}">
    <app:backButton url="${backUrl}"/>
  </c:if>
</form>