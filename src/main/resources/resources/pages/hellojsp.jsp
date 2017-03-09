<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
Spring URL: ${mvcjsp} at ${time}
<br>
JSTL URL: ${url}
<br>
Message: ${message}
</body>

</html>