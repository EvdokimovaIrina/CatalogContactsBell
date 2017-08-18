<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Не активные</title>
</head>
<body>
Пользователей приложения с количеством контактов меньше 10:<br>

<%
    UserService userService = UserServiceImpl.getInstance();
    String quantity = "";
    try {
        for (User user : userService.inactiveUsersList(10)) {%>
        <%= user.getLogin() %><br>

    <% }
    } catch (DaoException e) {%>
        <%= e.getMessage() %>
<% }%>

</body>
</html>
