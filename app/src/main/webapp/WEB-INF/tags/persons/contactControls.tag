<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="contact" required="true" type="com.exist.ecc.person.core.dto.ContactDto"%>

<span>
  (<a href="/contacts/${contact.contactId}/edit">edit</a>
  |
  <a href="/contacts/${contact.contactId}/delete">delete</a>)
</span>