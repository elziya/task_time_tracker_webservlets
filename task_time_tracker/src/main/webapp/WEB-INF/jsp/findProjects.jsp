<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:mainHeader>
    <div class="container-fluid" style="margin-top: 15px">
        <form class="d-flex" action="" method="post" id="form1">
            <input class="form-control me-2" name="searchByTag" required placeholder="Введите тег" aria-label="Search"
                   style="size: 100px">
            <button class="btn btn-outline-success" type="submit">Найти</button>
        </form>
        <form class="d-flex" action="" method="post" id="form2">
            <input class="form-control me-2" name="searchByName" required placeholder="Введите имя проекта или его часть" aria-label="Search"
                   style="size: 100px">
            <button class="btn btn-outline-success" type="submit">Найти</button>
        </form>
    </div>

    <c:if test="${projects.size() > 0}">

            <c:forEach items="${projects}" var="project">

                <div class="card border-info mb-3" style="max-width: 30rem; margin-left: 40px">
                    <div class="card-header" style="font-weight: bold;">${project.name}
                        <c:forEach items="${project.tags}" var="tag">
                            <span style="float: right;color: #075e7f">${tag.tagName}</span>
                        </c:forEach>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Потрачно: ${project.duration} минут <c:if test="${project.isDone()}">
                            <span style="float: right;color: #99F0D8"> СДЕЛАН </span></c:if></h5>
                        <p class="card-text">В период с ${project.startDate.getDayOfMonth()} ${project.startDate.getMonth()}
                                ${project.startDate.getYear()}-${project.endDate.getDayOfMonth()} ${project.endDate.getMonth()}
                                ${project.endDate.getYear()}</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${project.tasks}" var="task">
                            <li class="list-group-item">${task.name}<span style="float: right">${task.duration} минут</span></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
    </c:if>

    <c:if test="${message != null}">
        <h3 style="color: #075e7f">${message}</h3>
    </c:if>
</t:mainHeader>
