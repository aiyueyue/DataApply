<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2015/11/18
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private boolean hasPrivilege;
%>
<%
    //临时测试用：
    /*Map userSessionInfo = new HashMap();
    List<String> temp = new ArrayList<String>();
    temp.add("js/menu.testPrivilege.jsp");
    userSessionInfo.put("userName", "user1");
    userSessionInfo.put("userId", 1);
    userSessionInfo.put("userFunctionURL",temp);
    userSessionInfo.put("isLogin", true);
    session.setAttribute("userSessionInfo",userSessionInfo);*/
    //防止用户非法访问当前页面
    Map user = (Map) session.getAttribute("userSessionInfo");
    if(user == null)
    {
       //重定向方式待查
       response.sendRedirect("../login/login.jsp");
    }
    else
    {
        String currentUrl = null;
        if(request.getQueryString() != null) {
            currentUrl = request.getRequestURI() + "?" + request.getQueryString();
        }
        else
        {
            //currentUrl = request.getRequestURI();
            currentUrl = "../../js/menu/testPrivilege.jsp";
            //调试结果：/MapTest/js/***/***.jsp
        }
        boolean hasPrivilege = false;
        List<String> funUrl = (List<String>) user.get("userFunctionURL");
        for (int i = 0; i<funUrl.size(); i++)
        {
            String userPrivilegeUrl = (String)funUrl.get(i);
            if(currentUrl.equals(userPrivilegeUrl))
            {
                hasPrivilege = true;
            }
        }
        if(!hasPrivilege)
        {
            response.sendRedirect("http://192.168.7.234:8080/MapTest/js/menu/menu.jsp");
        }
    }
%>
<%--<script type="text/javascript" src="jquery-1.8.2.js"></script>
<script language="JavaScript">
    var serviceUrl = "http://192.168.7.234:8080/MapTest/";
    if((<%=hasPrivilege%>) == false)
    {
        document.getElementById("testBody").innerText("抱歉您没有该页面的权限，2秒后跳回")
        setTimeout(function(){
            window.location.href=serviceUrl + "js/menu/menu.jsp";
        },1000);
        //window.location.href = serviceUrl + "js/menu/menu.jsp";
    }
</script>--%>
<html>
<head>
    <title></title>
</head>
<body id="testBody">
    you have the right of this page
</body>
</html>
