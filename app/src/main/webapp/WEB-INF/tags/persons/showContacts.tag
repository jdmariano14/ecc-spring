<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>
<%@attribute name="contacts" required="true" type="java.util.Set"%>

<section class="ui segment">
  <h2>Contact info</h2>
  <ul>
  <c:forEach items="${contacts}" var="contact">
    <li>
      <strong><c:out value="${contact.type}"/>:</strong>
      <c:out value="${contact.info}"/>
      <persons:contactControls contact="${contact}"/>
    </li>
  </c:forEach>
  </ul>
  <p><a href="/persons/${person.personId}/contacts/new">Add contact</a></p>
</section>