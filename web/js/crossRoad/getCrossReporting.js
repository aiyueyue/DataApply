/**
 * Created by pangbo on 2015/12/15.
 */

/**
 * Created by pangbo on 2015/12/13.
 */

var serverUrl = "http://127.0.0.1:8080/";
//声明全局变量来保存表格数据
var globalTableData;

function getCrossRoadReportingData()
{
    $.ajax({
        async:false,
        cache:false,
        type: "post",
        dataType : "json",
        url: serverUrl + "getCrossRoadReporting.action",
        error: function () {
            alert('data load fail');
        },
        success:function(rs){
            showPieChart(rs);
            showTable(rs);
        }
    });
}
/**
 * 功能描述：用于设置饼图参数并显示饼图
 * @param rs
 */
function showPieChart(rs)
{
    var myChart = new FusionCharts("/FusionChart/swf/Pie2D.swf", "sectionPieChart", "100%", "100%");
    var chartData = rs.pieChartData;
    var strXML="<chart caption='交叉路口拥堵指数' showValues='0' showZeroPies='1' manageLabelOverflow='1' showBorder='0' bgAlpha='10'  baseFont='宋体' baseFontSize='12' >";
    var labels = new Array();
    labels[0] = "畅通";
    labels[1] = "预警";
    labels[2] = "拥堵";
    var values = [0,0,0];
    var totalValue = 0;
    for (var j = 0; j<chartData.length; j++)
    {
        values[chartData[j].STATUS] = chartData[j].COUNTNUM;
        totalValue += chartData[j].COUNTNUM;
    }
    //计算每个块所占的百分比,拼接得到自定义提示
    var toolTipText = new Array();
    for (var z = 0; z<3; z++)
    {
        toolTipText[z] = "";
        toolTipText[z] += labels[z] + ":占比";
        toolTipText[z] += (values[z]/totalValue * 100).toFixed(1) + "%";
    }

    for(var i=0;i<3;i++){
        var sectionNum = values[i];
        var label = labels[i] + ":" + values[i] + "个路口";
        strXML= strXML+"<set label='"+label +"'value='"+sectionNum + "'toolText='" + toolTipText[i]+"'" +"/>";
    }
    strXML= strXML+"</chart>";
    myChart.setDataXML(strXML);
    myChart.render("AidPie");
}
/**
 * 功能描述：用于页面中列表信息的生成
 * @param rs
 */
function showTable(rs) {
    var tableData = rs.tableData;
    globalTableData = rs.tableData;

    var congestionStatus = new Array();
    congestionStatus[0] = "畅通";
    congestionStatus[1] = "预警";
    congestionStatus[2] = "拥堵";

    for (var i = 0; i < tableData.length; i++) {
        var tableBody = "";
        tableBody += "<tr>";
        tableBody += "<td>" + tableData[i].INTERSECTION_NAME + "</td>";
        tableBody += "<td>" + (tableData[i].INTERSECTION_TPI_STATUS) + "</td>";
        tableBody += "<td>" + congestionStatus[tableData[i].INTERSECTION_TPI_STATUS] + "</td>";
        tableBody += "</tr>";
        $("#AidInfo").append(tableBody);
    }
}
/**
 * 功能描述：用于对报表的表格部分进行升序排序
 */
function asc(para)
{
    for (var index = globalTableData.length; index>=0; index--)
    {
        for(var index2 = 0; index2<index-1; index2++)
        {
            //对空数据的处理
            if(!(para in (globalTableData[index2])))
            {
                (globalTableData[index2])[para] = "未知";
            }
            if(!(para in (globalTableData[index2+1])))
            {
                (globalTableData[index2+1])[para] = "未知";
            }
            if((globalTableData[index2])[para] > (globalTableData[index2+1])[para])
            {
                var temp = globalTableData[index2];
                globalTableData[index2] = globalTableData[index2+1];
                globalTableData[index2+1] = temp;
            }
        }
    }
    var congestionStatus = new Array();
    congestionStatus[0] = "畅通";
    congestionStatus[1] = "预警";
    congestionStatus[2] = "拥堵";

    //清楚原有表格内容
    $("tbody").remove();
    //重新添加
    for (var i = 0; i < globalTableData.length; i++) {
        var tableBody = "";
        tableBody += "<tr>";
        tableBody += "<td>" + globalTableData[i].INTERSECTION_NAME + "</td>";
        tableBody += "<td>" + (globalTableData[i].INTERSECTION_TPI_STATUS) + "</td>";
        tableBody += "<td>" + congestionStatus[globalTableData[i].INTERSECTION_TPI_STATUS] + "</td>";
        tableBody += "</tr>";
        $("#AidInfo").append(tableBody);
    }
}
/**
 * 功能描述：用于对报表的表格部分进行降序排序
 */
function descrease(para)
{
    for (var index = globalTableData.length; index>=0; index--)
    {
        for(var index2 = 0; index2<index-1; index2++)
        {
            //对空数据的处理
            if(!(para in (globalTableData[index2])))
            {
                (globalTableData[index2])[para] = "未知";
            }
            if(!(para in (globalTableData[index2+1])))
            {
                (globalTableData[index2+1])[para] = "未知";
            }
            if((globalTableData[index2])[para] < (globalTableData[index2+1])[para])
            {
                var temp = globalTableData[index2];
                globalTableData[index2] = globalTableData[index2+1];
                globalTableData[index2+1] = temp;
            }
        }
    }
    var congestionStatus = new Array();
    congestionStatus[0] = "畅通";
    congestionStatus[1] = "预警";
    congestionStatus[2] = "拥堵";

    //清楚原有表格内容
    $("tbody").remove();
    //重新添加
    for (var i = 0; i < globalTableData.length; i++) {
        var tableBody = "";
        tableBody += "<tr>";
        tableBody += "<td>" + globalTableData[i].INTERSECTION_NAME + "</td>";
        tableBody += "<td>" + (globalTableData[i].INTERSECTION_TPI_STATUS) + "</td>";
        tableBody += "<td>" + congestionStatus[globalTableData[i].INTERSECTION_TPI_STATUS] + "</td>";
        tableBody += "</tr>";
        $("#AidInfo").append(tableBody);
    }
}
