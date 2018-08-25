/**
 * Created by dell on 2015/12/24.
 */
var serverUrl = "http://127.0.0.1:8080/";
var resultDataSetStat;
//每一页中要显示的表格的行数
var show_per_pageStat = 10;
var tableFootPagesNumberStat = 5;
//originStart originEnd currentStart currentEnd
//用于分页下标移动过程中分页栏的刷新控制
var originStartStat = 0;
var originEndStat;
var currentStartStat = 0;
var currentEndStat = tableFootPagesNumberStat - 1;
var theOrderTypeStat = "asc";
var theOrderKeyStat = "WARN_STAT_ID";
//搜索框输入内容的全局变量
var searchKeyStat = "";
var beginTimeStat="";
var endTimeStat="";
/**
 * 功能描述：从后台获取前端表格中需要显示的数据
 */
function initStatTable()
{
    $.ajax({
        async: false,
        catch: false,
        type: "post",
        dataType: "json",
        url:"createWarnStat.action?pageNumber=0&showPerPage=" + show_per_pageStat
        +"&searchKey=" + encodeURI(encodeURI(searchKeyStat))+"&beginTime="+beginTimeStat+"&endTime="+endTimeStat,
        error:function(){
            alert("fail");
        },
        success:function(result)
        {
            resultDataSetStat = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            //将整个数据集的大小放入到json的dataSize中
            var dataSize = result.dataSize;
            //将后台的orderType和orderKey再传回给前端，以便下一次请求使用
            theOrderTypeStat = result.orderType;
            theOrderKeyStat = result.orderKey;
            createTableFootStat(dataSize);
            //初始化首页默认的表格数据
            //var show_per_pageStat = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentStat(result);
        }
    })
}
/**
 * 功能描述：构建每一页的表格
 * @param currentPage
 * @param showPerPage
 */
function createTablePageStat(currentPageIndex,showPerPage)
{
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnStat.action?"+"orderKey="+theOrderKeyStat+"&orderType="+theOrderTypeStat +
        "&pageNumber=" + currentPageIndex + "&showPerPage=" + show_per_pageStat
        +"&searchKey=" + encodeURI(encodeURI(searchKeyStat))+"&beginTime="+beginTimeStat+"&endTime="+endTimeStat,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSetStat = result;
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentStat(result);
        }
    })
}

function orderINCStat(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //升序
    var orderType = "asc";
    //将排序相关的变量保存至全局变量
    theOrderKeyStat = orderBasedKey;
    theOrderTypeStat = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnStat.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType +
        "&pageNumber=0&showPerPage=" + show_per_pageStat +"&searchKey=" + encodeURI(encodeURI(searchKeyStat))+"&beginTime="+beginTimeStat+"&endTime="+endTimeStat,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSetStat = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootStat(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentStat(result);
        }
    })
}
function orderDESStat(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //降序
    var orderType = "desc";
    //将排序相关的变量保存至全局变量
    theOrderKeyStat = orderBasedKey;
    theOrderTypeStat = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:"createWarnStat.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType+
        "&pageNumber=0&showPerPage=" + show_per_pageStat+"&searchKey=" + encodeURI(encodeURI(searchKeyStat))+"&beginTime="+beginTimeStat+"&endTime="+endTimeStat,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSetStat = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootStat(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentStat(result);
        }
    })
}
/**
 * 功能描述：生成控制分页的导航栏
 * @param number_of_items
 */
function createTableFootStat(number_of_items)
{

    var number_of_pages = Math.ceil(number_of_items/show_per_pageStat);
    originEndStat = number_of_pages - 1;
    $('#current_page').val(0);
    $('#show_per_page').val(show_per_pageStat);
    var navigation_html = '<a class="toHead_link" href="javascript:toHeadStat()">Head</a>';
    navigation_html += '<a class="previous_link" href="javascript:previousStat();">Prev</a>';
    var current_link = 0;
    if(number_of_pages <= tableFootPagesNumberStat) {
        while (number_of_pages > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    else
    {
        while (tableFootPagesNumberStat > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    navigation_html += '<a class="next_link" href="javascript:nextStat();">Next</a>';
    navigation_html += '<a class="toTail_link" href="javascript:toTailStat()">Tail</a>';
    navigation_html += '<label>共'+ number_of_pages + '页</label>';
    $('#page_navigation').html(navigation_html);
    $('#page_navigation .page_link:first').addClass('active_page');

}
/**
 * 功能描述：跳转到前一页
 */
function previousStat(){
    new_page = parseInt($('#current_page').val()) - 1;
    if($('.active_page').prev('.page_link').length==true){
        go_to_pageStat(new_page);
    }
}
/**
 * 功能描述：跳转到后一页
 */
function nextStat(){
    new_page = parseInt($('#current_page').val()) + 1;
    //if there is an item after the current active link run the function
    if($('.active_page').next('.page_link').length==true){
        go_to_pageStat(new_page);
    }
}
/**
 * 功能描述：跳转到指定页
 * @param page_num
 */
function go_to_pageStat(page_num){

    //每次导航条进行跳转前依情况进行刷新操作
    refreshTableFootStat(page_num);
    //检测是否是首页或尾页，若是则禁用相应的向前或向后跳转的按钮
    if(page_num == 0)
    {
        $(".toHead_link").removeAttr('href');//去掉a标签中的href属性
        $(".toHead_link").css("color","grey");
        $(".previous_link").removeAttr('href');
        $(".previous_link").css("color","grey");

    }
    else
    {
        $(".toHead_link").attr('href',"javascript:toHeadStat()");
        $(".toHead_link").css("color","black");
        $(".previous_link").attr('href',"javascript:previousStat()");
        $(".previous_link").css("color","black");
    }
    var number_of_pages = Math.ceil(resultDataSetStat.dataSize/show_per_pageStat);
    if(page_num == number_of_pages - 1)
    {
        $(".toTail_link").removeAttr('href');//去掉a标签中的href属性
        $(".toTail_link").css("color","grey");
        $(".next_link").removeAttr('href');
        $(".next_link").css("color","grey");
    }
    else
    {
        $(".toTail_link").attr('href',"javascript:toTailStat()");//去掉a标签中的href属性
        $(".toTail_link").css("color","black");
        $(".next_link").attr('href',"javascript:nextStat()");
        $(".next_link").css("color","black");
    }
    //控制色块的跳转
    var showPerPage = parseInt($('#show_per_page').val());
    $('.page_link[longdesc=' + page_num +']').addClass('active_page').siblings('.active_page').removeClass('active_page');
    $('#current_page').val(page_num);
    //移除原始的页面
    $("tbody").remove();
    //创建新的表页
    createTablePageStat(page_num,showPerPage);
}

/**
 * 功能描述：跳到起始页
 */
function toHeadStat()
{
    go_to_pageStat(0);
}
/**
 * 功能描述：跳到末尾页
 */
function toTailStat()
{
    var show_per_pageStat = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSetStat.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_pageStat);
    go_to_pageStat(number_of_pages-1);
}
/**
 * 功能描述：根据当前选中的页面依情况刷新表尾的导航栏
 * @param page_num
 */
function refreshTableFootStat(page_num)
{
    var expandDistance = Math.floor(tableFootPagesNumberStat/2);
    var show_per_pageStat = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSetStat.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_pageStat);
    //需要重新调整导航栏的情况
    //导航条较短，不存在需要更新的情况
    if(originEndStat < currentEndStat)
    {
        return;
    }
    if(page_num - expandDistance <= originStartStat && currentStartStat != originStartStat)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadStat()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousStat();">Prev</a>';
        var start_link = 0;
        currentStartStat = start_link;
        var end_link = start_link + tableFootPagesNumberStat -1;
        currentEndStat = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextStat();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailStat()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance >= originEndStat && currentEndStat != originEndStat)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadStat()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousStat();">Prev</a>';
        var start_link = originEndStat - (tableFootPagesNumberStat-1);
        currentStartStat = start_link;
        var end_link = originEndStat;
        currentEndStat = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextStat();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailStat()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance < originEndStat && page_num + expandDistance > currentEndStat && currentEndStat != originEndStat)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadStat()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousStat();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStartStat = start_link;
        var end_link = start_link + tableFootPagesNumberStat - 1;
        currentEndStat = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextStat();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailStat()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num - expandDistance > originStartStat && page_num-expandDistance < currentStartStat && currentStartStat != originStartStat)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadStat()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousStat();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStartStat = start_link;
        var end_link = start_link + tableFootPagesNumberStat - 1;
        currentEndStat = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageStat(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextStat();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailStat()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
}
function showCurrentPageContentStat(result)
{
    //创建第一页的表格内容
    try {
        var datas = result.coreData;
        var start = 0;
        var end = start + show_per_pageStat;
        for (var i = start; i < end; i++) {
            var tbBody = "";
            var trColor;
            if (i % 2 == 0) {
                trColor = "evenLine";
            }
            else {
                trColor = "oddLine";
            }

            tbBody += "<tr class='" + trColor + "'>"
                + "<td>" + (('WARN_STAT_ID' in datas[i]) != false ? datas[i].WARN_STAT_ID : "--") + "</td>"
                + "<td class='device'><input type='hidden' class='deviceId' value='" + datas[i].DEVICE_ID + "'>" + (('DEVICE_ID' in datas[i]) != false ? datas[i].DEVICE_ID : "--") + "</td>"
                + "<td>" + (('DEVICE_TYPE' in datas[i]) != false ? datas[i].DEVICE_TYPE : "--") + "</td>"
                + "<td>" + (('OUGHT_DATA' in datas[i]) != false ? datas[i].OUGHT_DATA : "--") + "</td>"
                + "<td>" + (('LOSS_DATA' in datas[i]) != false ? datas[i].LOSS_DATA : "--") + "</td>"
                + "<td>" + (('ERROR_DATA' in datas[i]) != false ? datas[i].ERROR_DATA : "--") + "</td>"
                + "<td class='stat'><input type='hidden' class='statTime' value='"+datas[i].STAT_TIME+"'> " + (('STAT_TIME' in datas[i]) != false ? datas[i].STAT_TIME : "--") + "</td>"
                + "<td class='db'><input type='hidden' class='dbTime' value='"+datas[i].DB_TIME+"'>" + (('DB_TIME' in datas[i]) != false ? datas[i].DB_TIME : "--") + "</td>"
                + "<td>" + (('WARN_LEVEL' in datas[i]) != false ? datas[i].WARN_LEVEL : "--") + "</td>"
                + "<td>" + (('DEAL_STATUS' in datas[i]) != false ? datas[i].DEAL_STATUS : "--") + "</td>"
                + "<td>" + (('DEAL_USER' in datas[i]) != false ? datas[i].DEAL_STATUS : "--") + "</td>"
                + "<td>" + (('DEAL_DESC' in datas[i]) != false ? datas[i].DEAL_DESC : "--") + "</td>"
                + "<td><INPUT style='CURSOR: hand' type=image width=23px height=23px  src='/img/warnCenter/mingxi.jpg' border=0 name=Submit onclick='searchDetail(this)' class='submit'></td>"
            $("#warnStatTable").append(tbBody);
        }
        return true;
    }
    catch (e) {
        return false;
    }

}
/**
 * 点击搜索按钮后执行搜索功能
 */
function searchDataStat()
{
    var search=$("#filter").val();
    beginTimeStat=$("#beginTime").val();
    endTimeStat=$("#endTime").val();
    if(search == null)
    {
        alert("搜索关键字不可为空")
    }
    else{
        searchKeyStat = search;
        if(searchKeyStat!=null){
            //清除原来的表格内容和分页导航栏的内容
            $("tbody").remove();
            $("#page_navigation").empty();
            //依据搜索条件重新生成表格
            initStatTable();
        }
    }
}

function searchDataByTimeStat(){
    var search=$("#filter").val();
    beginTimeStat=$("#beginTime").val();
    endTimeStat=$("#endTime").val();
    if(beginTimeStat&&endTimeStat&&beginTimeStat>endTimeStat){
        alert("开始时间不能大于结束时间");
    }else{
        searchKeyStat = search;
        $("tbody").remove();
        $("#page_navigation").empty();
        initStatTable();
    }


}






