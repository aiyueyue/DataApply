<%--
  Created by IntelliJ IDEA.
  User: pangbo
  Date: 2015/11/17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>登录页面</title>
    <script type="text/javascript" src="jquery-1.8.2.js"></script>
    <script type="text/javascript" src="loginVerify.js"></script>
    <link href="css/login.css" rel="stylesheet" />
    <script language="JavaScript">
        function keyLogin() {
            //按Enter键的键值为13
            if (event.keyCode == 13) {
                //调用登录按钮的登录事件
                $(".input_02").click();
            }
        }
    </script>
</head>
<body onkeydown="keyLogin();">
<div class="login_tit"><img src="images/login_title.png"/></div>
<div class="login_main">
    <p>
        <span>用户名</span> <input id="userName" type="text" placeholder="用户名称" class="input_01"/>
    </p>
    <p>
        <span>密码</span> <input id="password" type="password" placeholder="密码" class="input_01"/>
    </p>
    <p><span></span><input type="button" value="" class="input_02" onclick="loginVerify()"/></p>
    <p id="errorMessage" style="text-align: center; margin-top: 10px; color: red"></p>
</div>
<div class="login_car"></div>
</body>
</html>
