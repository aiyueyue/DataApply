<%--
  Created by IntelliJ IDEA.
  User: pangbo
  Date: 2015/12/14
  Time: 17:00
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
    <title>交叉路口页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/map/klmap-core-1.0.css" type="text/css"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min.css?v=3.2.0" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/map/lib/OpenLayers.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/map/klmap-core-1.0.js"></script>
    <script src="${pageContext.request.contextPath}/js/map/klmap-utils-1.0.js"></script>
    <script src="${pageContext.request.contextPath}/FusionChart/fusionChartJs/FusionCharts.js" type="text/javascript" language="javascript"></script>
    <script src="${pageContext.request.contextPath}/js/crossRoad/getCrossReporting.js"></script>
    <script src="${pageContext.request.contextPath}/js/crossRoad/getCrossRoadImg.js"></script>

</head>
<body>
    <div id="mapDiv" style="float:left;">
        <div id="map" class="map"></div>
        <div class="zoom-up" style="left:0px;"><a href="javascript:map.zoomTo(map.getZoom()+1)"><div class="zoom-up-img"></div></a></div>
        <div class="zoom-down" style="left:0px;"><a href="javascript:map.zoomTo(map.getZoom()-1)"><div class="zoom-down-img"></div></a></div>
    </div>
    <div id="tableAndPieChart" style="overflow-y: auto; float:right; background-color:#fff; width:23.2%; height:100%;">
        <div id="AidPie" class="ibox float-e-margins" style="height: 35%; margin-bottom: 5px">
            <!-- 此处用于显示饼图-->
        </div>

        <div class="ibox float-e-margins">
            <!-- 此处用于显示表格的信息-->
            <div class="ibox-title">
                <h5>交叉路口信息(当前)</h5>
            </div>
            <div class="ibox-content">
                <table id="AidInfo" class="table table-hover">
                    <thead>
                    <tr>
                        <th width="34%">
                            <div class="sort">
                                路口名称
                                <a href="javascript:asc('INTERSECTION_NAME')">↑</a>
                                <a href="javascript:descrease('INTERSECTION_NAME')">↓</a>
                            </div>
                        </th>
                        <th width="33%">
                            <div class="sort">
                                拥堵指数
                                <a href="javascript:asc('INTERSECTION_TPI_STATUS')">↑</a>
                                <a href="javascript:descrease('INTERSECTION_TPI_STATUS')">↓</a>
                            </div>
                        </th>
                        <th width="33%">
                            <div class="sort">
                                拥堵状态
                            </div>
                        </th>
                    </tr>
                    </thead>
                    <!-- table body -->
                </table>
            </div>
        </div>
    </div>
</body>
</html>
