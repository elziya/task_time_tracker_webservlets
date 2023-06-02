<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainHeader>

    <h3 style="color:#075e7f;">Профиль</h3>
    <form class="account-form" style="margin-top: 10px; width: 500px">
        <div class="row">
            <div class="col">
                <input type="text" class="form-control" placeholder="Имя" value="${account.firstName}" disabled>
            </div>
            <div class="col">
                <input type="text" class="form-control" placeholder="Фамилия" value="${account.lastName}" disabled>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input type="email" class="form-control" placeholder="Email" value="${account.email}" disabled>
            </div>

        </div>
        <br>
        <form method="get">
            <button type="submit" class="btn btn-danger" name="action">Выйти</button>
        </form>

    </form>
</t:mainHeader>
