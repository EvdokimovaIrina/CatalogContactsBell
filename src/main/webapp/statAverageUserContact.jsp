<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сред. кол-во контактов</title>
</head>
<body>
<% UserService userService = UserServiceImpl.getInstance();
    String quantity="";
    try {
        quantity = String.valueOf(userService.averageUserContact());
    }catch (DaoException e){
        quantity=e.getMessage();
    }
%>
Среднее количество контактов у пользователей: <%= quantity %>
</body>
</html>
