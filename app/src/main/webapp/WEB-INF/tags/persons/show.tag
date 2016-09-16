<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="persons" tagdir="/WEB-INF/tags/persons"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>

<section class="ui segment">
  <p><strong>Address:</strong> <c:out value="${person.address.fullAddress}"/></p>
  <p><strong>Birth date:</strong> <c:out value="${person.birthDate}"/></p>
  <p><strong>Date hired:</strong> <c:out value="${person.dateHired}"/></p>
  <p><strong>Employed:</strong> <c:out value="${person.employed ? 'Yes' : 'No'}"/></p>
  <p><strong>GWA:</strong> <c:out value="${person.gwa}"/></p>
  <p><persons:controls person="${person}"/></p>
</section>
<persons:showRoles person="${person}" roles="${roles}"/>