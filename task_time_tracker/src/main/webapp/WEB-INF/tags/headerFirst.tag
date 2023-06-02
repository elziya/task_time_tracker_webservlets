<%@tag description="deafult layout tag" pageEncoding="UTF-16"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainLayout title="Главная">
    <nav class="navbar navbar-expand-lg navbar-light text-white" style="background-color: #075e7f">
        <div class="container-fluid">
            <a class="navbar-brand text-white" style=" color: #F0CA65;font-weight: bold;">
                T&Ttracker
            </a>
            <form class="d-flex">
                <a href="${pageContext.request.contextPath}/signin" class="btn btn-outline-light me-2">Войти</a>
                <a href="${pageContext.request.contextPath}/signup" class="btn btn-warning">Зарегистриоваться</a>
            </form>
        </div>
    </nav>
    <jsp:doBody/>
</t:mainLayout>
