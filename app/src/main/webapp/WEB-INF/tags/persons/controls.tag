<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@attribute name="person" required="true" type="com.exist.ecc.person.core.dto.PersonDto"%>

<app:primaryButton url="/persons/${person.personId}/edit" text="Edit" icon="write"/>
<app:primaryButton url="/persons/${person.personId}/delete" text="Delete" icon="trash outline"/>
<app:backButton url="/persons"/>