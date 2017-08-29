<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger=Logger.getLogger(this.getClass().getName()); %>
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
            synchronized (this) {
                for (Map<User, Integer> mapUser : userService.countingUserGroup()) {%>
    <tr>
        <% for (Map.Entry entry : mapUser.entrySet()) {
            User user = (User) entry.getKey();%>
        <td><%= user.getLogin() %></td>

        <td><%= entry.getValue() %></td>

        <%}%>
    </tr>
    <% }
    }
    } catch
            ( DaoException  e) {
        logger.error("Ошибка получения данных",e);%>
    <%= "Ошибка получения данных" %>
    <% }%>
    </tbody>
</table>
</body>
</html>
