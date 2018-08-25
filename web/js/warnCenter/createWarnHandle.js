/**
 * Created by wangliang on 2015/12/31.
 */

var serverUrl = "http://127.0.0.1:8080/";
var resultDataSet;
//每一页中要显示的表格的行数
var show_per_page = 10;
var tableFootPagesNumber = 5;
//originStart originEnd currentStart currentEnd
//用于分页下标移动过程中分页栏的刷新控制
var originStart = 0;
var originEnd;
var currentStart = 0;
var currentEnd = tableFootPagesNumber - 1;
var theOrderType = "asc";
var theOrderKey = "WARN_ID";
//搜索框输入内容的全局变量
var searchKey = "";
var beginTime="";
var endTime="";
var handleType="";
var handleStatus="";
var freshDate="";
/**
 * 功能描述：从后台获取前端表格中需要显示的数据
 */
function initTableHandle()
{
    $.ajax({
        async: false,
        catch: false,
        type: "post",
        dataType: "json",
        url:"createWarnHandle.action?pageNumber=0&showPerPage=" + show_per_page
        +"&searchKey=" + encodeURI(encodeURI(searchKey))+"&beginTime="+beginTime+"&endTime="+endTime+"&handleType="+encodeURI(encodeURI(handleType))+"&handleStatus="+handleStatus,
        error:function(){
            alert("fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            //将整个数据集的大小放入到json的dataSize中
            var dataSize = result.dataSize;
            //将后台的orderType和orderKey再传回给前端，以便下一次请求使用
            theOrderType = result.orderType;
            theOrderKey = result.orderKey;
            createTableFootHandle(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentHandle(result);
        }
    })
}
/**
 * 功能描述：构建每一页的表格
 * @param currentPage
 * @param showPerPage
 */
function createTablePageHandle(currentPageIndex,showPerPage)
{
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnHandle.action?"+"orderKey="+theOrderKey+"&orderType="+theOrderType +
        "&pageNumber=" + currentPageIndex + "&showPerPage=" + show_per_page+"&searchKey=" + encodeURI(encodeURI(searchKey))+"&beginTime="+beginTime+"&endTime="+endTime+"&handleType="+encodeURI(encodeURI(handleType))+"&handleStatus="+handleStatus,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentHandle(result);
        }
    })
}

function orderINCHandle(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //升序
    var orderType = "asc";
    //将排序相关的变量保存至全局变量
    theOrderKey = orderBasedKey;
    theOrderType = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnHandle.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType +
        "&pageNumber=0&showPerPage=" + show_per_page+"&searchKey=" + encodeURI(encodeURI(searchKey))+"&beginTime="+beginTime+"&endTime="+endTime+"&handleType="+encodeURI(encodeURI(handleType))+"&handleStatus="+handleStatus,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootHandle(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentHandle(result);
        }
    })
}
function orderDESHandle(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //降序
    var orderType = "desc";
    //将排序相关的变量保存至全局变量
    theOrderKey = orderBasedKey;
    theOrderType = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:"createWarnHandle.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType+
        "&pageNumber=0&showPerPage=" + show_per_page+"&searchKey=" + encodeURI(encodeURI(searchKey))+"&beginTime="+beginTime+"&endTime="+endTime+"&handleType="+encodeURI(encodeURI(handleType))+"&handleStatus="+handleStatus,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootHandle(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentHandle(result);
        }
    })
}
/**
 * 功能描述：生成控制分页的导航栏
 * @param number_of_items
 */
function createTableFootHandle(number_of_items)
{

    var number_of_pages = Math.ceil(number_of_items/show_per_page);
    originEnd = number_of_pages - 1;
    $('#current_page').val(0);
    $('#show_per_page').val(show_per_page);
    var navigation_html = '<a class="toHead_link" href="javascript:toHeadHandle()">Head</a>';
    navigation_html += '<a class="previous_link" href="javascript:previousHandle();">Prev</a>';
    var current_link = 0;
    if(number_of_pages <= tableFootPagesNumber) {
        while (number_of_pages > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    else
    {
        while (tableFootPagesNumber > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    navigation_html += '<a class="next_link" href="javascript:nextHandle();">Next</a>';
    navigation_html += '<a class="toTail_link" href="javascript:toTailHandle()">Tail</a>';
    navigation_html += '<label>共'+ number_of_pages + '页</label>';
    $('#page_navigation').html(navigation_html);
    $('#page_navigation .page_link:first').addClass('active_page');

}
/**
 * 功能描述：跳转到前一页
 */
function previousHandle(){
    new_page = parseInt($('#current_page').val()) - 1;
    if($('.active_page').prev('.page_link').length==true){
        go_to_pageHandle(new_page);
    }
}
/**
 * 功能描述：跳转到后一页
 */
function nextHandle(){
    new_page = parseInt($('#current_page').val()) + 1;
    //if there is an item after the current active link run the function
    if($('.active_page').next('.page_link').length==true){
        go_to_pageHandle(new_page);
    }
}
/**
 * 功能描述：跳转到指定页
 * @param page_num
 */
function go_to_pageHandle(page_num){

    //每次导航条进行跳转前依情况进行刷新操作
    refreshTableFootHandle(page_num);
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
        $(".toHead_link").attr('href',"javascript:toHeadHandle()");
        $(".toHead_link").css("color","black");
        $(".previous_link").attr('href',"javascript:previousHandle()");
        $(".previous_link").css("color","black");
    }
    var number_of_pages = Math.ceil(resultDataSet.dataSize/show_per_page);
    if(page_num == number_of_pages - 1)
    {
        $(".toTail_link").removeAttr('href');//去掉a标签中的href属性
        $(".toTail_link").css("color","grey");
        $(".next_link").removeAttr('href');
        $(".next_link").css("color","grey");
    }
    else
    {
        $(".toTail_link").attr('href',"javascript:toTailHandle()");//去掉a标签中的href属性
        $(".toTail_link").css("color","black");
        $(".next_link").attr('href',"javascript:nextHandle()");
        $(".next_link").css("color","black");
    }
    //控制色块的跳转
    var showPerPage = parseInt($('#show_per_page').val());
    $('.page_link[longdesc=' + page_num +']').addClass('active_page').siblings('.active_page').removeClass('active_page');
    $('#current_page').val(page_num);
    //移除原始的页面
    $("tbody").remove();
    //创建新的表页
    createTablePageHandle(page_num,showPerPage);
}

/**
 * 功能描述：跳到起始页
 */
function toHeadHandle()
{
    go_to_pageHandle(0);
}
/**
 * 功能描述：跳到末尾页
 */
function toTailHandle()
{
    var show_per_page = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSet.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_page);
    go_to_pageHandle(number_of_pages-1);
}
/**
 * 功能描述：根据当前选中的页面依情况刷新表尾的导航栏
 * @param page_num
 */
function refreshTableFootHandle(page_num)
{
    var expandDistance = Math.floor(tableFootPagesNumber/2);
    var show_per_page = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSet.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_page);
    //需要重新调整导航栏的情况
    //导航条较短，不存在需要更新的情况
    if(originEnd < currentEnd)
    {
        return;
    }
    if(page_num - expandDistance <= originStart && currentStart != originStart)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadHandle()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousHandle();">Prev</a>';
        var start_link = 0;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber -1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextHandle();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailHandle()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance >= originEnd && currentEnd != originEnd)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadHandle()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousHandle();">Prev</a>';
        var start_link = originEnd - (tableFootPagesNumber-1);
        currentStart = start_link;
        var end_link = originEnd;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextHandle();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailHandle()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance < originEnd && page_num + expandDistance > currentEnd && currentEnd != originEnd)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadHandle()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousHandle();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber - 1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextHandle();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailHandle()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num - expandDistance > originStart && page_num-expandDistance < currentStart && currentStart != originStart)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadHandle()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousHandle();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber - 1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageHandle(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextHandle();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailHandle()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
}
function showCurrentPageContentHandle(result)
{
    //创建第一页的表格内容
    try {
        var datas = result.coreData;

        var start = 0;
        var end = start + show_per_page;
        for (var i = start; i < end; i++) {
            var tbBody = "";
            var trColor;
            if (i % 2 == 0) {
                trColor = "evenLine";
            }
            else {
                trColor = "oddLine";
            }
            if(datas[i].DEAL_STATUS=="未处理") {
                tbBody += "<tr class='" + trColor + "'>"
                    + "<td class='warnId'><input type='hidden' class='selectWarnId' value='" + datas[i].WARN_ID + "'>" + (('WARN_ID' in datas[i]) != false ? datas[i].WARN_ID : "--") + "</td>"
                    + "<td>" + (('WARN_SRC' in datas[i]) != false ? datas[i].WARN_SRC : "--") + "</td>"
                    + "<td>" + (('WARN_TYPE' in datas[i]) != false ? datas[i].WARN_TYPE : "--") + "</td>"
                    //+ "<td>" + (('WARN_NO' in datas[i]) != false ? datas[i].WARN_NO : "--") + "</td>"
                    + "<td>" + (('WARN_SRC_TYPE' in datas[i]) != false ? datas[i].WARN_SRC_TYPE : "--") + "</td>"
                    + "<td>" + (('WARN_DESC' in datas[i]) != false ? datas[i].WARN_DESC : "--") + "</td>"
                    + "<td>" + (('CREATE_TIME' in datas[i]) != false ? datas[i].CREATE_TIME : "--") + "</td>"
                    + "<td>" + (('DB_TIME' in datas[i]) != false ? datas[i].DB_TIME : "--") + "</td>"
                    + "<td>" + (('WARN_STATUS' in datas[i]) != false ? datas[i].WARN_STATUS : "--") + "</td>"
                    + "<td>" + (('DEAL_STATUS' in datas[i]) != false ? datas[i].DEAL_STATUS : "--") + "</td>"
                    + "<td><INPUT style='CURSOR: hand' type=image width=23px height=23px  src='/img/warnCenter/chuli.png' border=0 name=Submit onclick='wornModifiy(this)' class='submit'></td>"
                    + "</tr>";
                $("#warningHandle").html("处理");
                $("#warningUser").hide();
            }else{
                tbBody += "<tr class='" + trColor + "'>"
                    + "<td class='warnId'><input type='hidden' class='selectWarnId' value='" + datas[i].WARN_ID + "'>" + (('WARN_ID' in datas[i]) != false ? datas[i].WARN_ID : "--") + "</td>"
                    + "<td>" + (('WARN_TYPE' in datas[i]) != false ? datas[i].WARN_TYPE : "--") + "</td>"
                    + "<td>" + (('WARN_SRC' in datas[i]) != false ? datas[i].WARN_SRC : "--") + "</td>"
                    //+ "<td>" + (('WARN_NO' in datas[i]) != false ? datas[i].WARN_NO : "--") + "</td>"
                    + "<td>" + (('WARN_SRC_TYPE' in datas[i]) != false ? datas[i].WARN_SRC_TYPE : "--") + "</td>"
                    + "<td>" + (('WARN_DESC' in datas[i]) != false ? datas[i].WARN_DESC : "--") + "</td>"
                    + "<td>" + (('CREATE_TIME' in datas[i]) != false ? datas[i].CREATE_TIME : "--") + "</td>"
                    + "<td>" + (('DB_TIME' in datas[i]) != false ? datas[i].DB_TIME : "--") + "</td>"
                    + "<td>" + (('WARN_STATUS' in datas[i]) != false ? datas[i].WARN_STATUS : "--") + "</td>"
                    + "<td>" + (('DEAL_STATUS' in datas[i]) != false ? datas[i].DEAL_STATUS : "--") + "</td>"
                    + "<td>" + (('DEAL_USER' in datas[i]) != false ? datas[i].DEAL_USER : "--") + "</td>"
                    + "<td>" + (('DEAL_DESC' in datas[i]) != false ? datas[i].DEAL_DESC : "--") + "</td>"
                    + "</tr>";
                $("#warningHandle").html("处理描述");
                $("#warningUser").show();
            }
            $("#warnDetailTable").append(tbBody);
        }
        return true;
    }
    catch(e)
    {
        return false;
    }
}
/**
 * 点击搜索按钮后执行搜索功能
 */
function searchDataHandle()
{
    var search=$("#filter").val();
    beginTime=$("#beginTime").val();
    endTime=$("#endTime").val();
    if(search == null)
    {
        alert("搜索关键字不可为空")
    }
    else{
        searchKey = search;
        if(searchKey!=null){
            //清除原来的表格内容和分页导航栏的内容
            $("tbody").remove();
            $("#page_navigation").empty();
            //依据搜索条件重新生成表格
            initTableHandle();
        }
    }
}
/**
 * 时间查询
 */
function searchDataByTimeHandle(){
    var search=$("#filter").val();
    beginTime=$("#beginTime").val();
    endTime=$("#endTime").val();
    handleType=document.getElementById("selectHandleType").value;
    handleStatus=document.getElementById("selectHandle").value;
    if(beginTime&&endTime&&beginTime>endTime){
        alert("开始时间不能大于结束时间");
    }else{
        searchKey = search;
        $("tbody").remove();
        $("#page_navigation").empty();
        initTableHandle();
    }


}

function changeHandle(){
    var search=$("#filter").val();
    beginTime=$("#beginTime").val();
    endTime=$("#endTime").val();
    handleType=document.getElementById("selectHandleType").value;
    handleStatus=document.getElementById("selectHandle").value;
    searchKey = search;
    $("tbody").remove();
    $("#page_navigation").empty();
    initTableHandle();
}

//$(document).ready(function(){
//    setInterval("warnRefresh()",5000);
//})

function warnRefresh(){
    var startDate=freshDate;
    $.ajax({
        async: false,
        cache: false,
        type: 'POST',
        dataType: "json",
        url: "warnFresh.action?pageNumber=0&showPerPage=" + show_per_page
        +"&startTime="+startDate,
        error: function () {
            alert('请求失败');
        },
        success: function (result) {
            freshDate=result.bigTime;
            var size=result.dataSize;
            if(size>0){
                $("#warningSubmit").show();
            }


        }
    })
}

$(document).ready(function(){
    $("#warningSubmit").click(function(){
        $(this).hide();
        //$(".J_iframe").attr("src","/pages/warnCenter/warnHandle.jsp")
        //$(".J_menuTab").attr("data-id","/pages/warnCenter/warnHandle.jsp");

    })



    setInterval("warnRefresh()",60000);
})

function wornModifiy(ob){
    parent.layer.prompt({title:"填写处理描述",formType:2,moveType:1},function(val){
        var dealDesc=val;
        var warnId=$(ob).parent().siblings(".warnId").children(".selectWarnId").val();
        dealDesc= $.trim(dealDesc);
        if(dealDesc==null||dealDesc==""){
            parent.alert("描述不能为空");
        }else {
            $.ajax({
                async: false,
                catch: false,
                type: "post",
                dataType: "json",
                url: "warnFresh.action?pageNumber=0&showPerPage=" + show_per_page
                + "&dealDesc=" + encodeURI(encodeURI(dealDesc))+"&warnId="+warnId,
                error: function () {
                    alert("fail");
                },
                success: function (result) {
                    var search=$("#filter").val();
                    beginTime=$("#beginTime").val();
                    endTime=$("#endTime").val();
                    handleType=document.getElementById("selectHandleType").value;
                    handleStatus=document.getElementById("selectHandle").value;
                    searchKey = search;
                    $("tbody").remove();
                    $("#page_navigation").empty();
                    initTableHandle()
                    parent.layer.msg("处理成功");
                }
            })
        }
    });

}




