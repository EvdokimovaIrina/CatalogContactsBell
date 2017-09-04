<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Авторизация</title>
</head>
<body>

<c:if test="${not empty param.login_error}">
  <span style="color: red; ">
    Не удалось авторизоваться .<br/><br/>
   <%-- по причине: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>--%>
  </span>
</c:if>

<form action="/j_spring_security_check" method="POST">
    <h3>Для просмотра контактов укажите регистрационные данные:</h3>
    <p>Введите имя пользователя: <input required type="text" name="username"></p>
    <p>Введите пароль: <input required type="password" name="password"></p>
    <input type="submit" value="Войти" />
</form>

</body>
</html>
