<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>

<a class="ui primary button" href="/persons/${person.personId}/edit">
  <i class="write icon"></i>
  Edit
</a>
<a class="ui primary button" href="/persons/${person.personId}/delete">
  <i class="trash outline icon"></i>
  Delete
</a>
<app:backButton url="/persons"/>