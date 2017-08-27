<%@ page import="org.apache.log4j.Logger" %>
<% Logger logger=Logger.getLogger("simple"); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Статистика</title>
</head>
<body>
<%
    logger.info("log message from TestLog JSP-page");
%>
    <div><a href=home>На главную</a></div>
    <p><a href=statNumberOfUsers.jsp> Общее количество пользователей. </a></p>
    <p><a href=statNumberOfUserContacts.jsp> Количество контактов каждого пользователя. </a></p>
    <p><a href=statNumberOfUserGroups.jsp> Количество групп каждого пользователя. </a></p>
    <p><a href=statAverageUserGroup.jsp> Среднее количество пользователей в группах. </a></p>
    <p><a href=statAverageUserContact.jsp> Среднее количество контактов у пользователей. </a></p>
    <p><a href=statInactiveUsers.jsp> Список неактивных пользователей приложения - количество контактов меньше 10 </a></p>
</body>
</html>
