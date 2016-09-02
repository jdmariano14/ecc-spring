<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>

<section class="ui segment">
  <p><strong>Name:</strong> <c:out value="${person.name.fullName}"/></p>
  <p><strong>Address:</strong> <c:out value="${person.address.fullAddress}"/></p>
  <p><strong>Birth date:</strong> <c:out value="${person.birthDate}"/></p>
  <p><strong>Date hired:</strong> <c:out value="${person.dateHired}"/></p>
  <p><strong>Employed:</strong> <c:out value="${person.employed ? 'Yes' : 'No'}"/></p>
  <p><strong>GWA:</strong> <c:out value="${person.gwa}"/></p>
</section>
<persons:showContacts person="${person}" contacts="${person.contacts}"/>
<persons:showRoles person="${person}" roles="${person.roles}"/>

<section class="ui segment">
  <p><a href="/persons/${person.personId}/contacts/new">Add contact</a></p>
</section>