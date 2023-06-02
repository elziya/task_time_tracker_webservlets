<%@tag description="deafult layout tag" pageEncoding="UTF-16"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true" %>
<html>
    <head>
        <title>${title}</title>
        <t:head/>
    </head>
    <body class="body">
        <jsp:doBody/>
        <t:footer/>
    </body>
</html>
