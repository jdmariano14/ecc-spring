<%@ page language="java" contentType="text/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1><c:out value="${person.name.fullName}"/></h1>

<p><strong>Address:</strong> <c:out value="${person.address.fullAddress}"/></p>
<p><strong>Birth date:</strong> <c:out value="${person.birthDate}"/></p>
<p><strong>Date hired:</strong> <c:out value="${person.dateHired}"/></p>
<p><strong>Employed:</strong> <c:out value="${person.employed ? 'Yes' : 'No'}"/></p>
<p><strong>GWA:</strong> <c:out value="${person.gwa}"/></p>

<p>
  <a href="/persons/${person.personId}/edit">Edit</a>
  |
  <a href="/persons/${person.personId}/delete">Delete</a>
  |
  <a href="/persons">Back</a>
</p>

<h2>Contact info</h2>
<ul>
<c:forEach items="${person.contacts}" var="contact">
  <li>
    <strong><c:out value="${contact.type}"/>:</strong>
    <c:out value="${contact.info}"/>
    <span>(<a href="/persons/${person.personId}/contacts/${contact.contactId}/edit">edit</a>
    |
    <a href="/contacts/${contact.contactId}/delete">delete</a>)</span>
  </li>
</c:forEach>
</ul>
<p><a href="/persons/${person.personId}/contacts/new">Add contact</a></p>

<h2>Roles</h2>
<ul>
<c:forEach items="${person.roles}" var="role">
  <li><c:out value="${role.name}"/> (<a href="/persons/${person.personId}/roles/${role.roleId}/delete">revoke</a>)</li>
</c:forEach>
</ul>
<p><a href="/persons/${person.personId}/roles/new">Grant role</p>