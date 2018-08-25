<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2015/12/7
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //防止用户非法访问当前页面
    Map user = (Map) session.getAttribute("userSessionInfo");
    if(user == null)
    {
        //重定向方式待查
        response.sendRedirect("http://127.0.0.1:8080/pages/login/login.jsp");
    }
%>
<!DOCTYPE html>
<html>

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>修改密码页面</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div style="padding-top:10%; width:300px;" class="middle-box text-center animated">
  <div>

    <h3>修改密码</h3>
    <!-- form 表单提交数据的形式可行但此处不合适-->
    <!--<form class="m-t" role="form" action="ChangePassword.action" method="post">-->
    <form id="cp" class="m-t">
      <div class="form-group">
        <input id="old" type="password" class="form-control" placeholder="旧密码" name="oldPassword">
      </div>
      <div class="form-group">
        <input id="new" type="password" class="form-control" placeholder="新密码" name="newPassword">
      </div>
      <div class="form-group">
        <input id="repeat" type="password" class="form-control" placeholder="重复密码" name="repeatPassword">
      </div>
      <input type="button" value="确  认" class="btn btn-primary block full-width m-b" onclick="changePasswordVerify()"/>
      <p id="errorMessage" style="text-align: center; margin-top: 10px; color: red"></p>
    </form>
  </div>
</div>
<!-- 全局js -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.4.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/layer.js"></script>

<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/js/login/changePassword.js"></script>
</body>
</html>
