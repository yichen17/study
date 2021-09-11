<%--
  Created by IntelliJ IDEA.
  User: E480
  Date: 2021/9/9
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登陆</title>
</head>
<body>
    <form action="/user/loginCheck">
        <label>用户名</label>
        <input type="text" placeholder="请输入用户名" id="username" name="username" value="banyu" >
        <label>密码</label>
        <input type="password" placeholder="请输入密码" id="password" name="password" value="7421">
        <button type="submit">登陆</button>
        <button type="reset">重置</button>
    </form>
</body>
</html>
