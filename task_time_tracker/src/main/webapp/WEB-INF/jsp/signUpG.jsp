<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:headerFirst>
    <script src="<c:url value="/js/signUpCheckFirst.js"/>"></script>

    <div class="d-flex justify-content-center align-items-center" style="margin-top: 10%">
        <form action="" method="post" id="form">

            <h1 class="text-center align-items-center">Зарегистрируйтесь</h1>

            <input name="email" type="email" class="form-control form_input" id="email" required placeholder="Email" value="${param.email}">

            <div id="emailHelp" class="form-text" style="color: red"> </div>
            <input name="firstName" type="text" class="form-control" id="firstName" required placeholder="Имя" value="${param.firstName}">

            <input name="lastName" type="text" class="form-control" id="lastName" required placeholder="Фамилия" value="${param.lastName}">

            <input name="password" type="password" class="form-control form_input" id="password" aria-describedby="passwordHelp"
                                                 required placeholder="Пароль">

            <small id="myPasswordHelp" class="form-text text-muted">Ваш пароль должен содержать как минимум восемь символов</small>

            <input name="password-repeat" id="password2"  required placeholder="Повторите пароль" type="password"
                       class="form-control form_input" aria-describedby="passwordHelp">

            <div id="passHelp" class="form-text" style="color: red"> </div>
            <c:if test="${not empty message}">
                <h6 class="text-center errorMessage">${message}</h6>
            </c:if>
            <br>
            <button id="signUpButton" type="submit" class="w-100 btn btn-lg btn-primary">Зарегистрироваться</button>
        </form>
    </div>

</t:headerFirst>
