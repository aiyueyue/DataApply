<%--
  Created by IntelliJ IDEA.
  User: pangbo
  Date: 2015/12/1
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userNo = request.getParameter("userNo");
    System.out.println(userNo);
    //防止用户非法访问当前页面
    //Map user = (Map) session.getAttribute(userNo);
    Map user = (Map) session.getAttribute("user");
    if(user==null || !(user.get("userNo").toString().equals(userNo)))
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
    <meta name="renderer" content="webkit">

    <title>智能交通采集系统</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <!-- 保存jsp中的变量值-->
    <!-- 修改12.21 -->
    <input type="hidden" id="userNo" value="<%=userNo%>"/>

    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span>&nbsp;&nbsp;&nbsp;<img alt="image" class="img-circle" src="../img/a0.png" /></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">&nbsp;&nbsp;&nbsp;&nbsp;keli</strong></span>
                                <span class="text-muted text-xs block">&nbsp;&nbsp;&nbsp;&nbsp;超级管理员<b class="caret"></b></span>
                                </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a id="changePassword" class="J_menuItem" href="changePassword.jsp">修改密码</a>
                            <%--<li><a id="changePassword" href="changePassword.html">修改密码</a>--%>
                            <li class="divider"></li>
                            <li><a href="javascript:logout();">安全退出</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">交通
                    </div>
                </li>
                <!--next is the created menu-->
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="index.jsp#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="请输入您需要查找的菜单 …" class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
                <div style="width: 40px;height: 40px;float: right;margin-bottom: 0px" class="warningDiv">
                    <a data-index="0" style="font-size:0px;" href="/pages/warnCenter/warnHandle.jsp" class="J_menuItem"><img style="width:30px;height:30px;margin-top: 10px " src="/img/warnCenter/warning.png" id='warningSubmit'/>
                        监控处理
                    </a>
                    <%--<INPUT style='CURSOR: hand' type=image width=30px height=30px  src='/img/warnCenter/warning.jpg' border=0 name=Submit id='warningSubmit'>--%>

                </div>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="/pages/index/html">交通状态评价</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="login/login.jsp" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="index_v1.html" frameborder="0" data-id="index_v1.html" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2008-2015 <a href="http://www.ahkeli.com/" target="_blank">安徽科力信息产业有限责任公司</a>
            </div>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.4.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/layer.js"></script>

<!-- 自定义js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu/createMenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login/logout.js"></script>
<script src="${pageContext.request.contextPath}/js/hplus.min.js?v=3.2.0"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/contabs.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/warnCenter/createWarnHandle.js"></script>

<!-- 第三方插件 -->
<script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>

</body>
</html>