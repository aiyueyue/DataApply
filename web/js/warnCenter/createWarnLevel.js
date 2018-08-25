/**
 * Created by wangliang on 2016/1/14.
 */
var serverUrl = "http://127.0.0.1:8080/";
var resultDataSetLevel;
//每一页中要显示的表格的行数
var show_per_pageLevel = 10;
var tableFootPagesNumberLevel = 5;
//originStart originEnd currentStart currentEnd
//用于分页下标移动过程中分页栏的刷新控制
var originStartLevel = 0;
var originEndLevel;
var currentStartLevel = 0;
var currentEndLevel = tableFootPagesNumberLevel - 1;
var theOrderTypeLevel = "asc";
var theOrderKeyLevel = "dic_id";
//搜索框输入内容的全局变量
var searchKeyLevel = "";
var beginTimeLevel="";
var endTimeLevel="";
var isValid=0;

/**
 * 功能描述：从后台获取前端表格中需要显示的数据
 */
function initTableLevel()
{
    $.ajax({
        async: false,
        catch: false,
        type: "post",
        dataType: "json",
        url:"createWarnLevel.action?pageNumber=0&showPerPage=" + show_per_pageLevel
        +"&searchKey=" + encodeURI(encodeURI(searchKeyLevel))+"&beginTime="+beginTimeLevel+"&endTime="+endTimeLevel+"&isValid="+isValid,
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
            theOrderTypeLevel = result.orderType;
            theOrderKeyLevel = result.orderKey;
            createTableFootLevel(dataSize);
            //初始化首页默认的表格数据
            //var show_per_pageLevel = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentLevel(result);
        }
    })
}
/**
 * 功能描述：构建每一页的表格
 * @param currentPage
 * @param showPerPage
 */
function createTablePageLevel(currentPageIndex,showPerPage)
{
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnLevel.action?"+"orderKey="+theOrderKeyLevel+"&orderType="+theOrderTypeLevel +
        "&pageNumber=" + currentPageIndex + "&showPerPage=" + show_per_pageLevel+"&searchKey=" + encodeURI(encodeURI(searchKeyLevel))+"&beginTime="+beginTimeLevel+"&endTime="+endTimeLevel+"&isValid="+isValid,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentLevel(result);
        }
    })
}

function orderINCLevel(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //升序
    var orderType = "asc";
    //将排序相关的变量保存至全局变量
    theOrderKeyLevel = orderBasedKey;
    theOrderTypeLevel = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:  "createWarnLevel.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType +
        "&pageNumber=0&showPerPage=" + show_per_pageLevel+"&searchKey=" + encodeURI(encodeURI(searchKeyLevel))+"&beginTime="+beginTimeLevel+"&endTime="+endTimeLevel+"&isValid="+isValid ,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootLevel(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentLevel(result);
        }
    })
}
function orderDESLevel(input)
{
    $("tbody").remove();
    var orderBasedKey = input;
    //降序
    var orderType = "desc";
    //将排序相关的变量保存至全局变量
    theOrderKeyLevel = orderBasedKey;
    theOrderTypeLevel = orderType;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url:"createWarnLevel.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType+
        "&pageNumber=0&showPerPage=" + show_per_pageLevel+"&searchKey=" + encodeURI(encodeURI(searchKeyLevel))+"&beginTime="+beginTimeLevel+"&endTime="+endTimeLevel+"&isValid="+isValid,
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFootLevel(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContentLevel(result);
        }
    })
}
/**
 * 功能描述：生成控制分页的导航栏
 * @param number_of_items
 */
function createTableFootLevel(number_of_items)
{

    var number_of_pages = Math.ceil(number_of_items/show_per_pageLevel);
    originEndLevel = number_of_pages - 1;
    $('#current_page').val(0);
    $('#show_per_page').val(show_per_pageLevel);
    var navigation_html = '<a class="toHead_link" href="javascript:toHeadLevel()">Head</a>';
    navigation_html += '<a class="previous_link" href="javascript:previousLevel();">Prev</a>';
    var current_link = 0;
    if(number_of_pages <= tableFootPagesNumberLevel) {
        while (number_of_pages > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    else
    {
        while (tableFootPagesNumberLevel > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    navigation_html += '<a class="next_link" href="javascript:nextLevel();">Next</a>';
    navigation_html += '<a class="toTail_link" href="javascript:toTailLevel()">Tail</a>';
    navigation_html += '<label>共'+ number_of_pages + '页</label>';
    $('#page_navigation').html(navigation_html);
    $('#page_navigation .page_link:first').addClass('active_page');

}
/**
 * 功能描述：跳转到前一页
 */
function previousLevel(){
    new_page = parseInt($('#current_page').val()) - 1;
    if($('.active_page').prev('.page_link').length==true){
        go_to_pageLevel(new_page);
    }
}
/**
 * 功能描述：跳转到后一页
 */
function nextLevel(){
    new_page = parseInt($('#current_page').val()) + 1;
    //if there is an item after the current active link run the function
    if($('.active_page').next('.page_link').length==true){
        go_to_pageLevel(new_page);
    }
}
/**
 * 功能描述：跳转到指定页
 * @param page_num
 */
function go_to_pageLevel(page_num){

    //每次导航条进行跳转前依情况进行刷新操作
    refreshTableFootLevel(page_num);
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
        $(".toHead_link").attr('href',"javascript:toHeadLevel()");
        $(".toHead_link").css("color","black");
        $(".previous_link").attr('href',"javascript:previousLevel()");
        $(".previous_link").css("color","black");
    }
    var number_of_pages = Math.ceil(resultDataSet.dataSize/show_per_pageLevel);
    if(page_num == number_of_pages - 1)
    {
        $(".toTail_link").removeAttr('href');//去掉a标签中的href属性
        $(".toTail_link").css("color","grey");
        $(".next_link").removeAttr('href');
        $(".next_link").css("color","grey");
    }
    else
    {
        $(".toTail_link").attr('href',"javascript:toTailLevel()");//去掉a标签中的href属性
        $(".toTail_link").css("color","black");
        $(".next_link").attr('href',"javascript:nextLevel()");
        $(".next_link").css("color","black");
    }
    //控制色块的跳转
    var showPerPage = parseInt($('#show_per_page').val());
    $('.page_link[longdesc=' + page_num +']').addClass('active_page').siblings('.active_page').removeClass('active_page');
    $('#current_page').val(page_num);
    //移除原始的页面
    $("tbody").remove();
    //创建新的表页
    createTablePageLevel(page_num,showPerPage);
}

/**
 * 功能描述：跳到起始页
 */
function toHeadLevel()
{
    go_to_pageLevel(0);
}
/**
 * 功能描述：跳到末尾页
 */
function toTailLevel()
{
    var show_per_pageLevel = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSet.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_pageLevel);
    go_to_pageLevel(number_of_pages-1);
}
/**
 * 功能描述：根据当前选中的页面依情况刷新表尾的导航栏
 * @param page_num
 */
function refreshTableFootLevel(page_num)
{
    var expandDistance = Math.floor(tableFootPagesNumberLevel/2);
    var show_per_pageLevel = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSet.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_pageLevel);
    //需要重新调整导航栏的情况
    //导航条较短，不存在需要更新的情况
    if(originEndLevel < currentEndLevel)
    {
        return;
    }
    if(page_num - expandDistance <= originStartLevel && currentStartLevel != originStartLevel)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadLevel()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousLevel();">Prev</a>';
        var start_link = 0;
        currentStartLevel = start_link;
        var end_link = start_link + tableFootPagesNumberLevel -1;
        currentEndLevel = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextLevel();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailLevel()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance >= originEndLevel && currentEndLevel != originEndLevel)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadLevel()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousLevel();">Prev</a>';
        var start_link = originEndLevel - (tableFootPagesNumberLevel-1);
        currentStartLevel = start_link;
        var end_link = originEndLevel;
        currentEndLevel = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextLevel();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailLevel()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance < originEndLevel && page_num + expandDistance > currentEndLevel && currentEndLevel != originEndLevel)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadLevel()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousLevel();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStartLevel = start_link;
        var end_link = start_link + tableFootPagesNumberLevel - 1;
        currentEndLevel = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextLevel();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailLevel()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num - expandDistance > originStartLevel && page_num-expandDistance < currentStartLevel && currentStartLevel != originStartLevel)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHeadLevel()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previousLevel();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStartLevel = start_link;
        var end_link = start_link + tableFootPagesNumberLevel - 1;
        currentEndLevel = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_pageLevel(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:nextLevel();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTailLevel()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
}
function showCurrentPageContentLevel(result)
{
    //创建第一页的表格内容
    try {
        var datas = result.coreData;
        var start = 0;
        var end = start + show_per_pageLevel;
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
                + "<td>" + (('WARN_LEVEL_ID' in datas[i]) != false ? datas[i].WARN_LEVEL_ID : "--") + "</td>"
                + "<td>" + (('WARN_LEVEL' in datas[i]) != false ? datas[i].WARN_LEVEL : "--") + "</td>"
                + "<td>" + (('WARN_LEVEL_DESC' in datas[i]) !=false ? datas[i].WARN_LEVEL_DESC : "--") +"</td>"
                + "<td>" + (('LOSE_TIME_MIN' in datas[i]) !=false ? datas[i].LOSE_TIME_MIN : "--") +"</td>"
                + "<td>" + (('LOSE_TIME_MAX' in datas[i]) !=false ? datas[i].LOSE_TIME_MAX : "--") +"</td>"
                + "<td>" + (('CREATE_USER' in datas[i]) != false ? datas[i].CREATE_USER : "--") + "</td>"
                + "<td>" + (('CREATE_TIME' in datas[i]) != false ? datas[i].CREATE_TIME : "--") + "</td>"
                + "<td>" + (('IS_VALID' in datas[i]) != false ? datas[i].IS_VALID:"--") + "</td>"
                + "</tr>";
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
function searchDataLevel()
{
    var search=$("#filter").val();
    beginTimeLevel=$("#beginTime").val();
    endTimeLevel=$("#endTime").val();
    if(search == null)
    {
        alert("搜索关键字不可为空")
    }
    else{
        searchKeyLevel = search;
        if(searchKeyLevel!=null){
            //清除原来的表格内容和分页导航栏的内容
            $("tbody").remove();
            $("#page_navigation").empty();
            //依据搜索条件重新生成表格
            initTableLevel();
        }
    }
}
/**
 * 时间查询
 */
function searchDataByTimeLevel(){
    var search=$("#filter").val();
    beginTimeLevel=$("#beginTime").val();
    endTimeLevel=$("#endTime").val();
    if( beginTimeLevel&&endTimeLevel&&beginTimeLevel>endTimeLevel){
        alert("开始时间不能大于结束时间");
    }else{
        searchKeyLevel = search;
        $("tbody").remove();
        $("#page_navigation").empty();
        initTableLevel();
    }


}

function changeLevel(){
    var search=$("#filter").val();
    beginTime=$("#beginTime").val();
    endTime=$("#endTime").val();
    isValid=document.getElementById("isValid").value;
    searchKey = search;
    $("tbody").remove();
    $("#page_navigation").empty();
    initTableLevel();
}



