<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>
<%@attribute name="contacts" required="true" type="java.util.List"%>

<section class="ui segment">
  <h2>Contact info</h2>
  <ul>
  <c:forEach items="${contacts}" var="contact">
    <li>
      <strong><c:out value="${contact.contactType}"/>:</strong>
      <c:out value="${contact.value}"/>
      <persons:contactControls contact="${contact}"/>
    </li>
  </c:forEach>
  </ul>
  <p><app:primaryButton url="/contacts/add?personId=${person.personId}" text="Add contact" icon="plus"/></p>
</section>