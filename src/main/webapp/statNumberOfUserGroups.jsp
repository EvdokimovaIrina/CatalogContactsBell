<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %><%--
  Created by IntelliJ IDEA.
  User: iren
  Date: 18.08.2017
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Количичество групп</title>
</head>
<body>
<h3>Количество групп каждого пользователя:</h3><br>
<table border="1">
    <thead>
    <tr>
        <th><h5> Пользователь </h5></th>
        <th><h5> Количество </h5></th>
    </tr>
    </thead>
    <tbody>
    <%
        UserService userService = UserServiceImpl.getInstance();
        try {
            synchronized(this) {
            for (User user : userService.countingUserGroup()) {%>
            <tr>
                 <td><%= user.getLogin() %></td>
                 <td><%= user.getQuantityGroup() %></td>
            </tr>
         <% }
        }
        }  catch (DaoException e) {%>
    <%= "Ошибка получения данных" %>
    <% }%>
    </tbody>
</table>
</body>
</html>
