<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:headerFirst>
    <div class="d-flex justify-content-center align-items-center" style="margin-top: 10%">
        <form action="" method="post">
            <h1 class="text-center align-items-center">Войдите</h1>
            <label for="email">Ваш Email:</label>
            <input name="email" type="email" class="form-control" id="email" required placeholder="myemail@gmail.ru" value="${param.email}">

            <label for="password">Пароль:</label>
            <input name="password" type="password" class="form-control" id="password" aria-describedby="passwordHelp" required placeholder="Введите пароль">
            <br>
            <button type="submit" class="w-100 btn btn-lg btn-primary">Войти</button>

            <c:if test="${not empty message}">
                <h6 class="text-center errorMessage">${message}</h6>
            </c:if>
        </form>
    </div>
</t:headerFirst>
