<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: E480
  Date: 2021/9/11
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Map<String,String> name = (Map<String, String>) request.getAttribute("res"); %>
<html>
<head>
    <title>Title</title>
    <h2><%=name.get("error")%></h2>
</head>
<body>

</body>
</html>
