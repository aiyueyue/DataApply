/**
 * Created by pangbo on 2015/11/20.
 */
var serverUrl = "http://192.168.7.234:8080/";
function getJsonData(chartType)
{
    $.ajax({
        async:true,
        cache:false,
        type: "post",
        dataType : "json",
        url: serverUrl + "getGraphData.action?" + "chartType=" + chartType,
        error: function () {
            alert('data load fail');
        },
        success:function(rs){
            if(chartType == 1)
            {
                showColumnChart(rs);
            }
            else if(chartType == 2)
            {
                showLineChart(rs);
            }
            else if(chartType == 3)
            {
                showPieChart(rs);
            }
            else
            {
                showFuelGauge(rs);
            }
        }
    });
}
function showColumnChart(rs)
{
    var myChart = new FusionCharts("swf/Column2D.swf", "myChartId", "400", "400");
    //myChart.setDataURL("data/data1.xml");
    //myChart.setDataXML(rs);
    var strXML="<chart caption='���õ�Ӱ����' showhovercap='1' xAxisName='��Ӱ��' yAxisName='count' baseFont='����' baseFontSize='12' >";
    var length=rs.length;
    for(var i=0;i<length;i++){
        strXML= strXML+"<set toolText='dsafd' link='JavaScript:Alert(1)' label='"+rs[i].name+"' value='"+rs[i].count+"'" + "/>";
    }
    strXML= strXML+"</chart>";
    myChart.setDataXML(strXML);
    myChart.render("columnChart");
}
function showLineChart(rs)
{
    var myChart = new FusionCharts("swf/Line.swf", "myChartId", "400", "400");
    //myChart.setDataURL("data/data1.xml");
    //myChart.setDataXML(rs);
    var strXML="<chart caption='���õ�Ӱ����' xAxisName='��Ӱ��' yAxisName='count' baseFont='����' baseFontSize='12' >";
    var length=rs.length;
    for(var i=0;i<length;i++){
        strXML= strXML+"<set label='"+rs[i].name+"' value='"+rs[i].count+"'" + "/>";
    }
    strXML= strXML+"</chart>";
    myChart.setDataXML(strXML);
    myChart.render("lineChart");
}
function showPieChart(rs)
{
    var myChart = new FusionCharts("swf/Pie2D.swf", "myChartId", "400", "400");
    //myChart.setDataURL("data/data1.xml");
    //myChart.setDataXML(rs);
    var strXML="<chart caption='���õ�Ӱ����' placeValuesInside='1' enableSmartLabels='0' xAxisName='��Ӱ��' yAxisName='count' baseFont='����' baseFontSize='12' >";
    var length=rs.length;
    for(var i=0;i<length;i++){
        strXML= strXML+"<set link='JavaScript:alert(1)' label='"+rs[i].name+"' value='"+rs[i].count+"'" + "/>";
    }
    strXML= strXML+"</chart>";
    myChart.setDataXML(strXML);
    //myChart.render("pieChart");
    myChart.render("regionPie");
}
function showFuelGauge(rs)
{
    var myChart = new FusionCharts("swf/AngularGauge.swf", "myChartId", "300", "150");
    var strXML =
    "<chart caption='Customer Satisfaction Score' bgcolor='ff0000' lowerlimit='0' upperlimit='100' lowerlimitdisplay='Bad' upperlimitdisplay='Good' palette='1' numbersuffix='%' tickvaluedistance='10' showvalue='0' gaugeinnerradius='0' bgcolor='FFFFFF' pivotfillcolor='333333' pivotradius='8' pivotfillmix='333333, 333333' pivotfilltype='radial' pivotfillratio='0,100' showtickvalues='1' showborder='0'>"
    + "<colorrange>" +
    " <color minvalue='0' maxvalue='45' code='e44a00' />" +
    "<color minvalue='45' maxvalue='75' code='f8bd19' />" +
    "<color minvalue='75' maxvalue='100' code='6baa01' />" +
    "</colorrange>" + "<dials>" +
    "<dial value='92' rearextension='15' radius='100' bgcolor='333333' bordercolor='333333' basewidth='8' />" +
    "</dials>" +
    "</chart>";
    myChart.setDataXML(strXML);
    myChart.render("fuelChart");
}
//�����۵�һ�����Զ��رյ���ʾ��
function Alert(str) {
    var msgw,msgh,bordercolor;
    msgw=350;//��ʾ���ڵĿ��
    msgh=80;//��ʾ���ڵĸ߶�
    titleheight=25; //��ʾ���ڱ���߶�
    bordercolor="#336699";//��ʾ���ڵı߿���ɫ
    titlecolor="#99CCFF";//��ʾ���ڵı�����ɫ
    var sWidth,sHeight;
    //��ȡ��ǰ���ڳߴ�
    sWidth = document.body.offsetWidth;
    sHeight = document.body.offsetHeight;
//    //����div
    /*var bgObj=document.createElement("div");
    bgObj.setAttribute('id','alertbgDiv');
    bgObj.style.position="absolute";
    bgObj.style.top="0";
    bgObj.style.background="#E8E8E8";
    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
    bgObj.style.opacity="0.6";
    bgObj.style.left="0";
    bgObj.style.width = sWidth + "px";
    bgObj.style.height = sHeight + "px";
    bgObj.style.zIndex = "10000";
    document.body.appendChild(bgObj);*/
    //������ʾ���ڵ�div
    var msgObj = document.createElement("div");
    msgObj.setAttribute("id","alertmsgDiv");
    msgObj.setAttribute("align","center");
    msgObj.style.background="white";
    msgObj.style.border="1px solid " + bordercolor;
    msgObj.style.position = "absolute";
    msgObj.style.left = "50%";
    //msgObj.style.left = event.clientX;
    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
    //���ھ������Ͷ��˵ľ���
    msgObj.style.marginLeft = "-225px";
    //���ڱ���ȥ�ĸ�+����Ļ���ù�������/2��-150
    msgObj.style.top = document.body.scrollTop+(window.screen.availHeight/2)-150 +"px";
    //msgObj.style.top = event.y - 150 + "px";
    msgObj.style.width = msgw + "px";
    msgObj.style.height = msgh + "px";
    msgObj.style.textAlign = "center";
    msgObj.style.lineHeight ="25px";
    msgObj.style.zIndex = "10001";
    document.body.appendChild(msgObj);
    //��ʾ��Ϣ����
    var title=document.createElement("h4");
    title.setAttribute("id","alertmsgTitle");
    title.setAttribute("align","left");
    title.style.margin="0";
    title.style.padding="3px";
    title.style.background = bordercolor;
    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
    title.style.opacity="0.75";
    title.style.border="1px solid " + bordercolor;
    title.style.height="18px";
    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";
    title.style.color="white";
    title.innerHTML="��ʾ��Ϣ";
    document.getElementById("alertmsgDiv").appendChild(title);
    //��ʾ��Ϣ
    var txt = document.createElement("p");
    txt.setAttribute("id","msgTxt");
    txt.style.margin="16px 0";
    txt.innerHTML = str;
    document.getElementById("alertmsgDiv").appendChild(txt);
    //���ùر�ʱ��
    window.setTimeout("closewin()",1000);
}
function closewin() {
    //document.body.removeChild(document.getElementById("alertbgDiv"));
    document.getElementById("alertmsgDiv").removeChild(document.getElementById("alertmsgTitle"));
    document.body.removeChild(document.getElementById("alertmsgDiv"));
}
