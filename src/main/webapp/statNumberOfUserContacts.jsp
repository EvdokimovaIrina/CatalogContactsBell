<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
      <title>Количичество контактов</title>
    </head>
<body>
<h3>Количество контактов каждого пользователя:</h3><br>
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
        for (User user : userService.countingUserContact()) {%>
        <tr>
            <td><%= user.getLogin() %></td>
            <td><%= user.getQuantityContact() %></td>
        </tr>
        <% }
}        catch (DaoException e) {%>
        <%= e.getMessage() %>
        <% }%>
        </tbody>
    </table>
</body>
</html>
