<%@ page import="catalogContacts.context.SpringUtils" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<% Logger logger = Logger.getLogger(this.getClass().getName());
    UserService userService;
%>
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
        try {
            synchronized (this) {
                userService = (UserService) SpringUtils.getContext().getBean("userServise");
                for (Map<User, Integer> mapUser : userService.countingUserGroup()) {%>
    <tr>
        <% for (Map.Entry entry : mapUser.entrySet()) {
            User user = (User) entry.getKey();%>
        <td><%= user.getLogin() %>
        </td>

        <td><%= entry.getValue() %>
        </td>

        <%}%>
    </tr>
    <% }
    }
    } catch
            (DaoException e) {
        logger.error("Ошибка получения данных", e);%>
    <%= "Ошибка получения данных" %>
    <% }%>
    </tbody>
</table>
</body>
</html>
