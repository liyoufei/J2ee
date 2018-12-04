<%--
  Created by IntelliJ IDEA.
  User: Sss
  Date: 2018/11/27
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String s = String.valueOf(request.getAttribute("message")); %>
<html>
<head>
    <title>error</title>
</head>
<body>
    <span><%= s %></span>
</body>
</html>
