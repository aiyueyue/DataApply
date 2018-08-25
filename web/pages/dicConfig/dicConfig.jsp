<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2015/12/17
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
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
        currentUrl = request.getRequestURI();
        boolean hasPrivilege = false;
        List<String> funUrl = (List<String>) user.get("userFunctionURL");
        for (int i = 0; i<funUrl.size(); i++)
        {
            String userPrivilegeUrl = (String)funUrl.get(i);
            if(currentUrl.equals(userPrivilegeUrl))
            {
                hasPrivilege = true;
                break;
            }
        }
        if(!hasPrivilege)
        {
            response.sendRedirect("http://127.0.0.1:8080/pages/index.jsp");
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>参数配置页面</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/footable/footable.core.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/dicConfig/tableCSS.css" rel="stylesheet">
</head>
<body class="gray-bg">
    <!-- 控制分页 -->
    <input type="hidden" id="current_page"/>
    <input type="hidden" id="show_per_page"/>

    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>参数配置</h5>
            <div class="ibox-tools">
                <a class="collapse-link">
                    <i class="fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="ibox-content">
            <div>
                <input style="float:right;width:90%;" type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="按参数名称进行搜索...">
                <input style="float:left;width:10%;height:30px" type="button" value="搜 索" onclick="searchData()">
            </div>

            <table class="footable table table-stripped" id="dicConfigTable">
                <thead>
                    <tr>
                        <th width="16%">
                            参数编号
                            <a style="margin-left: 15px;" href="javascript:orderINC('DIC_ID')">↑</a>
                            <a href="javascript:orderDES('DIC_ID')">↓</a>
                        </th>
                        <th width="16%">
                            父参数编号
                            <a style="margin-left: 15px;" href="javascript:orderINC('PARENT_ID')">↑</a>
                            <a href="javascript:orderDES('PARENT_ID')">↓</a>
                        </th>
                        <th width="20%">
                            参数名称
                            <a style="margin-left: 15px;" href="javascript:orderINC('DIC_CH_NAME')">↑</a>
                            <a href="javascript:orderDES('DIC_CH_NAME')">↓</a>
                        </th>
                        <th width="16%">
                            参数值
                            <a style="margin-left: 15px;" href="javascript:orderINC('DIC_VALUE')">↑</a>
                            <a href="javascript:orderDES('DIC_VALUE')">↓</a>
                        </th>
                        <th width="16%">
                            参数描述
                            <a style="margin-left: 15px;" href="javascript:orderINC('DIC_DESC')">↑</a>
                            <a href="javascript:orderDES('DIC_DESC')">↓</a>
                        </th>
                        <th width="16%">
                            修改参数值
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr><td id="page_navigation" colspan="6" align="right"></td></tr>
                </tfoot>
            </table>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/footable/footable.all.min.js"></script>
    <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
    <script src="${pageContext.request.contextPath}/js/dicConfig/createTable.js" language="javascript" type="text/javascript"></script>
    <script type="text/javascript">
        initTable();
    </script>
</body>
</html>
