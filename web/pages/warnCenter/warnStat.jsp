<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2015/12/24
  Time: 10:39
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
  <title>监控统计</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/plugins/footable/footable.core.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/system/tableCSS.css" rel="stylesheet">
  <script src="/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
</head>
<body class="gray-bg">
<!-- 控制分页 -->
<input type="hidden" id="current_page"/>
<input type="hidden" id="show_per_page"/>

<%
  String deviceID=request.getParameter("deviceId")==null?"":request.getParameter("deviceId");
  String beginTime=request.getParameter("beginTime")==null?"":request.getParameter("beginTime");
  String endTime=request.getParameter("endTime")==null?"":request.getParameter("endTime");
  String searchKey=request.getParameter("searchKey")==null?"":request.getParameter("searchKey");
  System.out.println("ccc="+deviceID+" -- "+beginTime+"-- "+endTime+"--"+searchKey);
%>
<input type="hidden" id="deviceIdStat" value="<%=deviceID%>">
<input type="hidden" id="beginTimeStat" value="<%=beginTime%>">
<input type="hidden" id="endTimeStat" value="<%=endTime%>">
<input type="hidden" id="searchKeyStat" value="<%=java.net.URLDecoder.decode(searchKey,"utf-8")%>">

<div class="ibox float-e-margins" id="bigDiv">
  <div class="ibox-title">
    <h5>监控统计</h5>
    <div class="ibox-tools">
      <a class="collapse-link">
        <i class="fa fa-chevron-up"></i>
      </a>
    </div>
  </div>
  <div class="ibox-content">
    <div>
      <dt>起止时间：</dt>
      <div style="width:30%;float: left" >
        <dd>
          <input  type="text" placeholder="选择开始时间" id="beginTime"  onFocus="WdatePicker()" onblur="searchDataByTimeStat()" readonly="false"   style='height:30px;width:110px;' >-<input  type="text" placeholder="选择结束时间" id="endTime"  onFocus="WdatePicker()"  readonly="false"   style='height:30px;width:110px;' >
        </dd>
      </div>
      <div style="width: 30%;float: right">
        <input style="float:left;width:90%;" type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="输入条件搜索(设备类型)。。">
        <input style="float:right;width:10%;height:30px" type="button" value="搜 索" onclick="searchDataStat()">
      </div>
    </div>

    <table class="footable table table-stripped" id="warnStatTable">
      <thead>
      <tr>
        <th w>
          警告统计ID
        </th>
        <th >
          设备编码
        </th>
        <th >
          设备类型
        </th>
        <th >
          应收数据
          <a style="margin-left: 15px;" href="javascript:orderINCStat('OUGHT_DATA')">↑</a>
          <a href="javascript:orderDESStat('OUGHT_DATA')">↓</a>
        </th>
        <th >
          丢失数据
          <a style="margin-left: 15px;" href="javascript:orderINCStat('LOSS_DATA')">↑</a>
          <a href="javascript:orderDESStat('LOSS_DATA')">↓</a>
        </th>
        <th >
          错误数据
          <a style="margin-left: 15px;" href="javascript:orderINCStat('ERROR_DATA')">↑</a>
          <a href="javascript:orderDESStat('ERROR_DATA')">↓</a>
        </th>
        <th >
          统计时间
          <a style="margin-left: 15px;" href="javascript:orderINCStat('STAT_TIME')">↑</a>
          <a href="javascript:orderDESStat('STAT_TIME')">↓</a>
        </th>
        <th >
          入库时间
          <a style="margin-left: 15px;" href="javascript:orderINCStat('DB_TIME')">↑</a>
          <a href="javascript:orderDESStat('DB_TIME')">↓</a>
        </th>
        <th>
          警告等级
          <a style="margin-left: 15px;" href="javascript:orderINCStat('temptable5.WARN_LEVEL')">↑</a>
          <a href="javascript:orderDESStat('temptable5.WARN_LEVEL')">↓</a>
        </th>
        <th >
          处理状态
        </th>
        <th >
          处理人
        </th>
        <th >
          处理描述
        </th>
        <th>
          查看明细
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
<script src="${pageContext.request.contextPath}/js/warnCenter/createWarnStat.js" language="javascript" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/warnCenter/createWarnDetail.js" language="javascript" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"language="javascript" type="text/javascript"></script>
<script type="text/javascript">
  deviceIdStat=$("#deviceIdStat").val();
  beginTimeStat=$("#beginTimeStat").val();
  endTimeStat=$("#endTimeStat").val();
  searchKeyStat=$("#searchKeyStat").val();
  document.getElementById("beginTime").value=beginTimeStat;
  document.getElementById("endTime").value=endTimeStat;
  document.getElementById("filter").value=searchKeyStat;
  initStatTable();

  function searchDetail(ob){
    var detail=$(ob).parent().siblings(".device").children(".deviceId").val();
    var bgtime=$(ob).parent().siblings(".stat").children(".statTime").val();
    var entime=$(ob).parent().siblings(".db").children(".dbTime").val();
    var bgTime=$("#beginTime").val();
    var enTime=$("#endTime").val();
    var search=$("#filter").val();
     window.location.href="/pages/warnCenter/warnDetail.jsp?deviceId="+detail+"&beginTime="+bgtime+"&endTime="+entime+"&searchKey="+encodeURI(encodeURI(search))+"&showStartTime="+bgTime+"&showEndTime="+enTime;
//    $("#bigDiv").load("/pages/warnCenter/warnDetail.jsp",{deviceId:deviceId});
    //$("tbody").remove();
    //$("#page_navigation").empty();
    //initTable();

  }

</script>
</body>
</html>

