<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainHeader>
    <div class="container-fluid" style="margin-top: 15px">
        <form class="d-flex" method="post" id="form-create">
            <input type="text" name="tagName" required placeholder="Название тега" style="size: 100px">
            <button class="btn btn-outline-success" type="submit">Создать</button>
        </form>
    </div>

    <c:if test="${tags.size() > 0}">

        <c:forEach items="${tags}" var="tag">
            <div class="card" style="width: 10rem;">
                <div class="card-body">
                    <h5 class="card-title">${tag.tagName}</h5>
                </div>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${message != null}">
        <h3 style="color: #075e7f">${message}</h3>
    </c:if>

</t:mainHeader>
