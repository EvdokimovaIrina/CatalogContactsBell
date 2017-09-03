<%@ page import="catalogContacts.context.SpringUtils" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger = Logger.getLogger(this.getClass().getName());
    UserService userService;
%>
<html>
<head>
    <title>Сред. кол-во контактов</title>
</head>
<body>
<%
    String quantity = "";
    try {
        synchronized (this) {
            userService = (UserService) SpringUtils.getContext().getBean("userServise");
            quantity = String.valueOf(userService.averageUserContact());
        }
    } catch (DaoException e) {
        logger.error("Ошибка получения данных", e);
        quantity = "Ошибка получения данных";
    }
%>
Среднее количество контактов у пользователей: <%= quantity %>
</body>
</html>
