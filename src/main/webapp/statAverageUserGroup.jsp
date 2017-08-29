<%@ page import="catalogContacts.service.UserService" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger=Logger.getLogger(this.getClass().getName()); %>
<html>
<head>
    <title>Сред. кол-во групп</title>
</head>
<body>
<% UserService userService = UserServiceImpl.getInstance();
    String quantity="";
    try {
        synchronized(this) {
            quantity = String.valueOf(userService.averageUserGroup());
        }
    }catch (DaoException e){
        logger.error("Ошибка получения данных",e);
        quantity="Ошибка получения данных";
    }
%>
Среднее количество пользователей в группах: <%= quantity %>
</body>
</html>
