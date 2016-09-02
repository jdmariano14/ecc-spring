<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.model.wrapper.PersonWrapper"%>

<a href="/persons/${person.personId}/edit">Edit</a>
|
<a href="/persons/${person.personId}/delete">Delete</a>
|
<a href="/persons">Back</a>