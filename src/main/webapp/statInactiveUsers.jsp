<%@ page import="catalogContacts.context.SpringUtils" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.model.User" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger = Logger.getLogger(this.getClass().getName());
    UserService userService;
%>
<html>
<head>
    <title>Не активные</title>
</head>
<body>
Пользователей приложения с количеством контактов меньше 10:<br>

<%
    String quantity = "";
    try {
        synchronized (this) {
            userService = (UserService) SpringUtils.getContext().getBean("userServise");
            for (User user : userService.inactiveUsersList(10)) {%>
<%= user.getLogin() %><br>

<% }
}
} catch (DaoException e) {
    logger.error("Ошибка получения данных", e);
%>
<%= "Ошибка получения данных" %>
<% }%>

</body>
</html>
