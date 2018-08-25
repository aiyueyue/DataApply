/**
 * Created by pangbo on 2015/12/14.
 */
var popup;
var pointLayer;
var obj;
var serverUrl = "http://127.0.0.1:8080/";

jQuery(document).ready(function(){
    //设置报表部分的内容
    getCrossRoadReportingData();
    //设置地图窗口大小的自动调整
    resizeDiv();
    window.onresize=resizeDiv;
    //初始化地图
    initMap();
    //请求交叉口及点击交叉口时弹出的明细数据
    $.ajax({
        url: serverUrl +"getCrossRoadInfo.action",
        async:true,
        cache:false,
        type: "post",
        dataType : "json",
        error: function () {
            alert('data load fail');
        },
        success: function(result){
            obj = result;
            addAidFun(result);
        }
    });
});

/**
 * 功能描述：在交叉口添加图标及点击图标后的响应事件
 * @param arr
 */
function addAidFun(arr){
    //new
    var crossMapInfo = arr.crossMapInfo;
    var sectionDirect = new Array();
    sectionDirect[1] = "离开路口";
    sectionDirect[2] = "进入路口";
    //
    var autoSizeFramedCloudMinSize;
    var popupClass ;
    var feature;
    if(pointLayer!=null){
        pointLayer.destroy();
    }
    $("ul").empty();
    pointLayer = new OpenLayers.Layer.Markers("Point Layer");
    map.addLayer(pointLayer);
    var str='';
    for(var i=0;i<crossMapInfo.length;i++){
        var aidName=crossMapInfo[i].INTERSECTION_NAME;
        var x=crossMapInfo[i].X;
        var y=crossMapInfo[i].Y;
        var status=crossMapInfo[i].INTERSECTION_TPI_STATUS;
        var aidId=crossMapInfo[i].INTERSECTION_ID;
        var sampleNum = crossMapInfo[i].SAMPLENUM;
        var warningTime = crossMapInfo[i].DB_TIME;
        var sectionInfos = crossMapInfo[i].sectionInfos;
        //var lkms=arr[i].LKMS;
        //var sectionId=arr[i].SECTIONID;
        //var sectionName=arr[i].SECTIONNAME;
        //var sectionFx=arr[i].SECTIONFX;
        //var travelType=arr[i].TRAVELTYPE;
        //var travelName=getTravelName(travelType);
        //var time=arr[i].STIME;
        var iconUrl="";
        var ydzk="";
        if(status=="1") {
            iconUrl=serverUrl+"img/cross/warning.png";
            ydzk="预警";
        }else if(status=="2"){
            iconUrl=serverUrl+"img/cross/congestion.png";
            ydzk="拥堵";
        }
        var iconWidth=16;
        var iconHeigth=16;

        if(status!=0){
            //var tip="路口名称："+aidName+"\n路段名称："+sectionName+"\n方向："+sectionFx+"\n拥堵状况："+ydzk+"\n时间"+time;
            //var tip="路口名称："+aidName+"\n路口编号："+aidId+"\n"+lkms;
            var tip="路口名称："+aidName;
            var autoSizeFramedCloudMinSize = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {'autoSize': true});
            var popupClass = autoSizeFramedCloudMinSize;
            //构造点击预警图标后弹出框中的html
            var popupContentHTML = "<table border='1' style=\"border-color:#1ab394;\">";
            popupContentHTML += "<tr><td colspan='4'><b>路口信息如下:</b></td></tr>";
            popupContentHTML += "<tr><td colspan='4'>路口名称: " + aidName + "<br>";
            popupContentHTML += "路口拥堵状态：" + ydzk + "<br>";
            popupContentHTML += "样本数：" + sampleNum + "<br>";
            popupContentHTML += "时间：" + getWarningTime(warningTime);
            popupContentHTML += "</td></tr>";
            popupContentHTML += "<tr><td colspan='4'><b>路口对应路段信息如下:</b></td></tr>";
            popupContentHTML += "<tr><td><b>路段编号</b></td><td><b>路段名称</b></td><td><b>路段方向</b></td><td><b>路段长度</b></td></tr>";
            for (var j = 0; j<sectionInfos.length; j++)
            {
                popupContentHTML += "<tr><td>"+ sectionInfos[j].SECTIONID + "</td>";
                popupContentHTML += "<td>"+ sectionInfos[j].NAME + "</td>";
                popupContentHTML += "<td>"+ sectionDirect[sectionInfos[j].DIRECTION] + "</td>";
                popupContentHTML += "<td>"+ sectionInfos[j].LENGTH.toFixed(1) + "m"+"</td></tr>";
            }
            //var popupContentHTML = "路口名称："+aidName+"<br>路口编号："+aidId;
            popupContentHTML += "</table>";
            var feature = getPopupFeature(pointLayer, x, y, popupClass, popupContentHTML, true);
            addPoint(pointLayer, x, y , aidId, 'pointName',feature,null,iconWidth,iconHeigth,iconUrl,-11,-10,true,null,false,null,null,null,tip);
        }
    }
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
function getWarningTime(time)
{
    var timeString = "" + time.hours + ":" + time.minutes + ":" + time.seconds;
    return timeString;
}