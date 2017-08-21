<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Общее количество пользователей</title>
</head>
<body>
<% UserService userService = UserServiceImpl.getInstance();
    String quantity="";
    try {
        synchronized(this) {
            quantity = String.valueOf(userService.numberOfUsers());
        }
    }catch (DaoException e){
        quantity=e.getMessage();
    }
%>
Общее количество пользователей: <%= quantity %>

</body>
</html>
