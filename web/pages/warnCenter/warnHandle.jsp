<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2015/12/31
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  //防止用户非法访问当前页面
//  Map user = (Map) session.getAttribute("userSessionInfo");
//  if(user == null)
//  {
//    //重定向方式待查
//    response.sendRedirect("../login/login.jsp");
//  }
//  else
//  {
//    String currentUrl = null;
//    currentUrl = request.getRequestURI();
//    boolean hasPrivilege = false;
//    List<String> funUrl = (List<String>) user.get("userFunctionURL");
//    for (int i = 0; i<funUrl.size(); i++)
//    {
//      String userPrivilegeUrl = (String)funUrl.get(i);
//      if(currentUrl.equals(userPrivilegeUrl))
//      {
//        hasPrivilege = true;
//        break;
//      }
//    }
//    if(!hasPrivilege)
//    {
//      response.sendRedirect("http://127.0.0.1:8080/pages/index.jsp");
//    }
//  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>监控处理</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/plugins/footable/footable.core.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/system/tableCSS.css" rel="stylesheet">
</head>
<body class="gray-bg">
<!-- 控制分页 -->
<input type="hidden" id="current_page"/>
<input type="hidden" id="show_per_page"/>
<div class="ibox float-e-margins">
  <div class="ibox-title">
    <h5>监控处理</h5>
    <div class="ibox-tools">
      <a class="collapse-link">
        <i class="fa fa-chevron-up"></i>
      </a>
    </div>
  </div>
  <div class="ibox-content">
    <div class="headld">
      <dt>起止时间：</dt>
      <div style="width:60%;float: left" >
        <dd>
          <input  type="text" placeholder="选择开始时间" id="beginTime"  onFocus="WdatePicker()" onblur="searchDataByTimeHandle()" readonly="false"   style='height:30px;width:110px;' >-<input  type="text" placeholder="选择结束时间" id="endTime"  onFocus="WdatePicker()"  readonly="false"   style='height:30px;width:110px;' >
          <select id="selectHandleType"style="height: 30px;width:100px;margin-left: 30px" onchange="changeHandle()">
            <option value="">全部</option>
            <option value="软件">软件</option>
            <option value="数据">数据</option>
            <option value="设备">设备</option>
          </select>
          <select id="selectHandle" style="height: 30px;width:100px;margin-left: 10px" onchange="changeHandle()">
            <option value="0">未处理</option>
            <option value="1">已处理</option>
          </select>
        </dd>
      </div>
      <div style="width: 30%;float: right">
        <input style="float:left;width:90%;" type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="输入条件搜索(路段、类型、公司等)。。">
        <input style="float:right;width:10%;height:30px" type="button" value="搜 索" onclick="searchDataHandle()">
      </div>
    </div>

    <table class="footable table table-stripped" id="warnDetailTable">
      <thead>
      <tr>
        <th >
          警告ID
        </th>
        <th >
          警告来源(设备ID)
        </th>
        <%--<th >--%>
          <%--警告编码--%>
        <%--</th>--%>
        <th >
          警告类型
        </th>
        <th >
          警告来源类型
        </th>
        <th >
          警告描述
        </th>
        <th >
          创建时间
          <a style="margin-left: 15px;" href="javascript:orderINCHandle('CREATE_TIME')">↑</a>
          <a href="javascript:orderDESHandle('CREATE_TIME')">↓</a>
        </th>
        <th >
          入库时间
          <a style="margin-left: 15px;" href="javascript:orderINCHandle('DB_TIME')">↑</a>
          <a href="javascript:orderDESHandle('DB_TIME')">↓</a>
        </th>
        <th>
          警告状态
          <a style="margin-left: 15px;" href="javascript:orderINCHandle('temptable3.WARN_STATUS')">↑</a>
          <a href="javascript:orderDESHandle('temptable3.WARN_STATUS')">↓</a>
        </th>
        <th >
          处理状态
        </th>
        <th id="warningUser">
          <div>处理人</div>
        </th>
        <th >
          <div id="warningHandle">处理</div>
        </th>
      </tr>
      </thead>
      <tfoot>
      <tr><td id="page_navigation" colspan="6" align="right"></tr>
      </tfoot>
    </table>
  </div>
</div>


<!-- 全局js -->
<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layer.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.4.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/footable/footable.all.min.js"></script>
<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/js/content.min.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/warnCenter/createWarnHandle.js" language="javascript" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"language="javascript" type="text/javascript"></script>
<script type="text/javascript">
  handleType=document.getElementById("selectHandleType").value;
  handleStatus=document.getElementById("selectHandle").value;
  initTableHandle();
</script>
</body>
</html>
