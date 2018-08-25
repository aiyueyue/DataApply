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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>交通信息采集系统登录页面</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
    <script language="JavaScript">
        function keyLogin() {
            //按Enter键的键值为13
            if (event.keyCode == 13) {
                //调用登录按钮的登录事件
                $(".btn-primary").click();
            }
        }
    </script>
</head>
<body class="gray-bg" onkeydown="keyLogin();">
    <%--<div class="login_main">
        <p>
            <span>用户名</span> <input id="userName" type="text" placeholder="用户名称" class="input_01"/>
        </p>
        <p>
            <span>密码</span> <input id="password" type="password" placeholder="密码" class="input_01"/>
        </p>
        <p><span></span><input type="button" value="" class="input_02" onclick="loginVerify()"/></p>
        <p id="errorMessage" style="text-align: center; margin-top: 10px; color: red"></p>
    </div>--%>
    <div class="middle-box text-center loginscreen  animated">
        <div>
            <div>
                <font size="60" >南 京</font>
            </div>
            <h3>欢迎使用交通信息采集系统</h3>
            <form class="m-t" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" id="userName" placeholder="用户名" required="required" value="keli">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="password" placeholder="密码">
                </div>
                <%--<button type="submit" class="btn btn-primary block full-width m-b" onclick="loginVerify()">登  录</button>--%>
                <input type="button" value="登  录" class="btn btn-primary block full-width m-b" onclick="loginVerify()"/>
                <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a>
                </p>
                <p id="errorMessage" style="text-align: center; margin-top: 10px; color: red"></p>
            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.4.0"></script>

    <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/login/loginVerify.js"></script>
</body>
</html>
