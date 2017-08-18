<%@ page import="catalogContacts.controller.impl.ControllerJSPImpl" %>
<%@ page import="catalogContacts.controller.ControllerJSP" %>
<%@ page import="catalogContacts.dao.exception.DaoException" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="ShowError.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Общее количество пользователей</title>
</head>
<body>
<% ControllerJSP controllerJSP = new ControllerJSPImpl();
    String quantity="";
    try {
        quantity = String.valueOf(controllerJSP.numberOfUsers());
    }catch (DaoException e){
        throw new RuntimeException(e.getMessage());
    }

%>
Общее количество пользователей:
<%= quantity %><br>
</body>
</html>
