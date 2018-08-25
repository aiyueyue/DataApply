<%--
  Created by IntelliJ IDEA.
  User: pangbo
  Date: 2015/12/7
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <title>Section Map</title>
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
    <script src="${pageContext.request.contextPath}/js/section/getPieChartAndTableData.js"></script>
    <script language="javascript" type="text/javascript"><!--
    var popup;
    var pointLayer;
    var sslkLayer;
    var marker;

    jQuery(document).ready(function(){
        //获取饼图和报表的数据
        getPieChartAndTableData();
        //重置DIV大小
        resizeDiv();window.onresize=resizeDiv;
        //初始化地图
        initMap();
        //添加一个title
        //addPointAndtitleDemo();
        //添加一条线(及虚线)
        //addLineDemo();
        //添加一个点
        //addPointDemo();
        //添加一个弹窗口
        //addPopupDemo();
        //添加一个弹窗口(云团样式)
        //addPopupCloudDemo();
        //添加一个点带popup
        //addPointAndPopupDemo();
        //添加一个点带popup2
        //addPointAndPopupDemo2();
        //地图添加点击事件
        //addClickEvent();
        //添加实时路况-----------------
        addSslk();
        map.events.register("zoomend", null, addSslk); //地图缩放获得实时路况 //暂不使用
        //map.events.register("moveend", null, sslk); //移动地图获得实时路况
        //map.events.register("click", null, function(e){
        //var lonLat = getLonLatFromPixel(e.x, e.y);
        //lonLat = getLonLatForServer(lonLat.lon, lonLat.lat);
        //console.log("x="+lonLat.lon+",y="+lonLat.lat);
        //});
        //setInterval(addSslk,300000);
        //-----------------------------
        //添加实时路况For Image //调试中
        //addSslkImage();
        //添加点显示ToolTips
        //addPointAndToolTipsDemo();
        //添加文本标注 //测试中
        //addTextDemo();
        //添加路径规划点
        //pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        //map.addLayer(pointLayer);
        //map.events.register("click", null, function(e){
        //var lonLat = getLonLatFromPixel(e.x, e.y);
        //lonLat = getLonLatForServer(lonLat.lon, lonLat.lat);
        //console.log("x="+lonLat.lon+",y="+lonLat.lat);
        //addPointDemo2(lonLat.lon, lonLat.lat);
        //});
        /*map.events.register("click", null, function(e){
         alert(e.x+","+e.y);
         });*/
    });

    //添加一条线
    function addLineDemo(){
        var lineLayer = new OpenLayers.Layer.Vector("Line Layer");
        map.addLayer(lineLayer);
        //var strPoint = "117.36145,31.897326,117.359498,31.893117,117.361493,31.897308,117.367351,31.907855,117.369304,31.911771,117.377973,31.938433";
        //addLine(lineLayer, strPoint, '#FF0000', 0.3, 5);

        //黄山路（玉兰大道-香樟大道）
        var strPoint1 = "117.184126,31.841859,117.184369,31.841761,117.185428,31.841861,117.1857,31.84188,117.1875,31.84194,117.189242,31.842014";
        //黄山路（香樟大道-玉兰大道）
        var strPoint2 = "117.189243,31.842174,117.1875,31.842114,117.186221,31.842067,117.185382,31.842074,117.184312,31.842,117.184126,31.841859";
        addLine(lineLayer, strPoint1, '#FF0000', 0.3, 5);
        addLine(lineLayer, strPoint2, '#FF0000', 0.3, 5, "dot"); //虚线
    }
    //添加一个点
    function addPointDemo(){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);
        addPoint(pointLayer, 117.28519, 31.863563, 'pointId', 'pointName', null, pointClick);
        //addPoint(pointLayer, 117.188637, 31.846044, 'pointId', 'pointName', null, pointClick);
        //添加一个弹窗
        //addPopupDemo();
    }
    function addPointDemo(x, y){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);
        addPoint(pointLayer, x, y, 'pointId', 'pointName');
    }
    function addPointDemo2(x, y){
        if(marker != null)
            pointLayer.removeMarker(marker);
        marker = addPoint(pointLayer, x, y, 'pointId', 'pointName');
    }
    function pointClick(evt){
        popup.toggle();
        //this.destroy();
        //addPoint(pointLayer, this.x, this.y, 'pointId2', 'pointName2', pointClick, 28, 28, 'test.gif');
        //alert(this.id+":"+this.name);
    }
    //添加一个弹窗口
    function addPopupDemo(){
        popup = getPopup(popup, "popup", 117.28519, 31.863563, 200, 200, "<font color='red'>Mickey</font>");
        map.addPopup(popup);
        //popup.show();
    }
    //添加一个弹窗口(云团样式)
    function addPopupCloudDemo(){
        popup = getPopupCloud(popup, "popup", 117.28519, 31.863563, 200, 200, "<font color='red'>Mickey</font>");
        map.addPopup(popup);
        popup.show();
    }
    //添加一个点带popup
    function addPointAndPopupDemo(){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);
        var autoSizeFramedCloudMinSize = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {'autoSize': true});
        var popupClass = autoSizeFramedCloudMinSize;
        var popupContentHTML = '<font color=red>Hi Mickey!</font>';
        var feature = getPopupFeature(pointLayer, 117.28519, 31.863563, popupClass, popupContentHTML, true);
        addPoint(pointLayer, 117.28519, 31.863563, 'pointId', 'pointName', feature);
    }
    //添加一个点带popup2
    function addPointAndPopupDemo2(){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);
        addPoint(pointLayer, 117.28519, 31.863563, 'pointId', 'pointName', null, addPointAndPopupDemo2CallBack);
    }
    function addPointAndPopupDemo2CallBack(evt){
        var x = this.x;
        var y = this.y;
        var id = this.id;
        //alert("x="+x+",y="+y+",id="+id);
        //this.destroy();
        popup = getPopupCloud(popup, "popup", 117.28519, 31.863563, 200, 200, "<font color='red'>Mickey</font>");
        map.addPopup(popup);
    }
    //地图添加点击事件（获得点击点经纬度）
    function addClickEvent(){
        map.events.register("click", null, function(e){
            var lonLat = getLonLatFromPixel(e.x, e.y);
            //alert(lonLat.lon+","+lonLat.lat);

            pointLayer = new OpenLayers.Layer.Markers("Point Layer");
            map.addLayer(pointLayer);
            addPoint(pointLayer, lonLat.lon, lonLat.lat, 'pointId', 'pointName', null, null, null, null, null, null, null, false);
        });
    }
    //添加实时路况
    function sslk(){
        if(sslkLayer != null)
            sslkLayer.destroy();
        setTimeout(addSslk,80);
    }
    function addSslk(){
        if(map.getZoom()<minZoom || map.getZoom()>maxZoom) return;
        if(sslkLayer != null)
            sslkLayer.destroy();
        sslkLayer = new OpenLayers.Layer.Markers("Sslk Layer");
        map.addLayer(sslkLayer);
        var lonlat = map.getCenter().transform(
                map.getProjectionObject(), map.displayProjection
        )
        //console.log(lonlat.lon+","+lonlat.lat);
        //实时路况调用
        addSslkImg(sslkLayer, lonlat.lon, lonlat.lat, map.getZoom(), map.div.scrollWidth, map.div.scrollHeight);
        //预测路况调用 预测时间  five  ten  fifteen  thirty （thirty为未来30分钟路况）
        //addSslkImg(sslkLayer, lonlat.lon, lonlat.lat, map.getZoom(), map.div.scrollWidth, map.div.scrollHeight, "thirty");
    }
    //添加实时路况Image //调试中
    function addSslkImage(){
        //var bounds = map.getExtent();
        var bounds = new OpenLayers.Bounds(
                0,0,1440,760
        );
        var base_layer = new OpenLayers.Layer.Image(
                '地图',
                'sslkImg.png',
                bounds,
                new OpenLayers.Size(1440,760)
        );
        map.addLayer(base_layer);
        //map.zoomToMaxExtent();
    }
    //添加点显示ToolTips
    function addPointAndToolTipsDemo(){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);

        var autoSizeFramedCloudMinSize = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {'autoSize': true});
        var popupClass = autoSizeFramedCloudMinSize;
        var popupContentHTML = '<font color=red>Hi Mickey!</font>';
        var feature = getPopupFeature(pointLayer, 117.28519, 31.863563, popupClass, popupContentHTML, true);
        addPoint(pointLayer, 117.28519, 31.863563, 'pointId', 'pointName', feature, null, null, null, null, null, null, null,
                null, true, null, null, 'hi mickey!');

        var autoSizeFramedCloudMinSize = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {'autoSize': true});
        var popupClass = autoSizeFramedCloudMinSize;
        var popupContentHTML = '<font color=red>Hi Minnie!</font>';
        var feature = getPopupFeature(pointLayer, 117.26519, 31.873563, popupClass, popupContentHTML, true);
        addPoint(pointLayer, 117.26519, 31.873563, 'pointId', 'pointName', feature, null, null, null, null, null, null, null,
                null, true, null, null, 'hi minnie!');
    }
    //添加文本标注 //测试中
    function addTextDemo(){
        var lonlat = new OpenLayers.LonLat(117.28519,  31.863563).transform(
                map.displayProjection, map.getProjectionObject()
        );
        var pois = new OpenLayers.Layer.Text( "My Points", {
            location:"./textfile.txt",
            projection: map.displayProjection
        });
        map.addLayer(pois);
    }
    //添加一个title
    function addPointAndtitleDemo(){
        pointLayer = new OpenLayers.Layer.Markers("Point Layer");
        map.addLayer(pointLayer);

        //addPoint(pointLayer, 117.28519, 31.863563, 'pointId', 'pointName', null, null, null, null, null, null, null, null, null, null, null, null, null,"火车站话话火车站话话");
        addPoint(pointLayer,  117.188637, 31.846044, 'pointId', 'pointName', null, null, null, null, null, null, null, null, null, null, null, null, null,"火车站话话火车站话话");

    }
    /*重置DIV大小*/
    function resizeDiv(){
        var width,height;
        //设置地图显示区域div的大小为屏幕宽度的76.8%
        width = jQuery("body").width()*0.768;
        height = jQuery("body").height();
        //alert(width+","+height);
        jQuery("#mapDiv").width(width);
        jQuery("#mapDiv").height(height);
    }
    --></script>
</head>
<body>
    <div id="mapDiv" style="float:left;">
        <div id="map" class="map"></div>
        <div class="zoom-up" style="left:0px;"><a href="javascript:map.zoomTo(map.getZoom()+1)"><div class="zoom-up-img"></div></a></div>
        <div class="zoom-down" style="left:0px;"><a href="javascript:map.zoomTo(map.getZoom()-1)"><div class="zoom-down-img"></div></a></div>
    </div>
    <div id="tableAndPieChart" style="overflow-y: auto; float:right; background-color:#fff; width:23.2%; height:100%;">
            <div id="regionPie"class="ibox float-e-margins" style="height: 35%; margin-bottom: 5px">
                <!-- 此处用于显示饼图-->
            </div>

            <div class="ibox float-e-margins">
                <!-- 此处用于显示表格的信息-->
                <div class="ibox-title">
                    <h5>路段信息(当前)</h5>
                </div>
                <div class="ibox-content">
                    <table id="sectionInfo" class="table table-hover">
                        <thead>
                        <tr>
                            <th width="34%">
                                <div class="sort">
                                    路段名称
                                    <a href="javascript:asc('SECTION_NAME')">↑</a>
                                    <a href="javascript:descrease('SECTION_NAME')">↓</a>
                                </div>
                            </th>
                            <th width="33%">
                                <div class="sort">
                                    拥堵指数
                                    <a href="javascript:asc('SECTION_TPI')">↑</a>
                                    <a href="javascript:descrease('SECTION_TPI')">↓</a>
                                </div>
                            </th>
                            <th width="33%">
                                <div class="sort">
                                    拥堵等级
                                    <a href="javascript:asc('SECTION_TPI_STATUS')">↑</a>
                                    <a href="javascript:descrease('SECTION_TPI_STATUS')">↓</a>
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

