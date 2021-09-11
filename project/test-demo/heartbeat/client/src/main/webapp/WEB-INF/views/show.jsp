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
    <h1> 登陆成功</h1>
    <h2><%=name.get("msg")%></h2>
</head>
<body>

</body>
<script type="text/javascript">
    window.οnlοad=function(){
        console.log("start");
        <%--window.localStorage.setItem("secret","<%=name.get("secret") %>");--%>
        <%--console.log("插入浏览器缓存成功");--%>
    }
</script>
</html>
