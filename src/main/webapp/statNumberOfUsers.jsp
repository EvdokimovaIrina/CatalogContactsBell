<%@ page import="catalogContacts.context.SpringUtils" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ page import="catalogContacts.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="catalogContacts.service.impl.UserServiceImpl" %>
<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger=Logger.getLogger(this.getClass().getName());
UserService userService;
%>

<html>
<head>
    <title>Общее количество пользователей</title>
</head>
<body>
<%
    String quantity="";
    try {
        synchronized(this) {
            userService = (UserService) SpringUtils.getContext().getBean("userServise");
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
