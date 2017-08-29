<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger=Logger.getLogger(this.getClass().getName()); %>

<html>
<head>
    <title>Общее количество пользователей</title>
</head>
<body>
<% UserService userService = UserServiceImpl.getInstance();
    String quantity="";
    try {
        synchronized(this) {
            quantity = String.valueOf(userService.countingUsers());
        }
    }catch (DaoException e){
        logger.error("Ошибка получения данных",e);
        quantity="Ошибка получения данных";
    }
%>
Общее количество пользователей: <%= quantity %>

</body>
</html>
