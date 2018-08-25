/**
 * Created by pangbo
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
var theOrderKey = "dic_id";
//搜索框输入内容的全局变量
var searchKey = "";
/**
 * 功能描述：从后台获取前端表格中需要显示的数据
 */
function initTable()
{
    $.ajax({
        async: false,
        catch: false,
        type: "post",
        dataType: "json",
        url: serverUrl + "getDicConfigData.action?pageNumber=0&showPerPage=" + show_per_page
              + "&searchKey=" + encodeURI(encodeURI(searchKey)),
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
            createTableFoot(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContent(result);
        }
    })
}
/**
 * 功能描述：构建每一页的表格
 * @param currentPage
 * @param showPerPage
 */
function createTablePage(currentPageIndex,showPerPage)
{
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"json",
        url: serverUrl + "getDicConfigData.action?"+"orderKey="+theOrderKey+"&orderType="+theOrderType +
        "&pageNumber=" + currentPageIndex + "&showPerPage=" + show_per_page
        + "&searchKey=" + encodeURI(encodeURI(searchKey)),
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContent(result);
        }
    })
}

function orderINC(input)
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
        url: serverUrl + "getDicConfigData.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType +
                          "&pageNumber=0&showPerPage=" + show_per_page
                            + "&searchKey=" + encodeURI(encodeURI(searchKey)),
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFoot(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContent(result);
        }
    })
}
function orderDES(input)
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
        url: serverUrl + "getDicConfigData.action?"+"orderKey="+orderBasedKey+"&orderType="+orderType+
                        "&pageNumber=0&showPerPage=" + show_per_page + "&searchKey=" + encodeURI(encodeURI(searchKey)),
        error:function(){
            alert("sort fail");
        },
        success:function(result)
        {
            resultDataSet = result;
            //获取数据集的大小,并根据该值创建表尾的导航
            var dataSize = result.dataSize;
            createTableFoot(dataSize);
            //初始化首页默认的表格数据
            //var show_per_page = parseInt($('#show_per_page').val());
            //var current_page = parseInt($('#current_page').val());
            showCurrentPageContent(result);
        }
    })
}
/**
 * 功能描述：生成控制分页的导航栏
 * @param number_of_items
 */
function createTableFoot(number_of_items)
{

    var number_of_pages = Math.ceil(number_of_items/show_per_page);
    originEnd = number_of_pages - 1;
    $('#current_page').val(0);
    $('#show_per_page').val(show_per_page);
    var navigation_html = '<a class="toHead_link" href="javascript:toHead()">Head</a>';
    navigation_html += '<a class="previous_link" href="javascript:previous();">Prev</a>';
    var current_link = 0;
    if(number_of_pages <= tableFootPagesNumber) {
        while (number_of_pages > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    else
    {
        while (tableFootPagesNumber > current_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + current_link + ')" longdesc="' + current_link + '">' + (current_link + 1) + '</a>';
            current_link++;
        }
    }
    navigation_html += '<a class="next_link" href="javascript:next();">Next</a>';
    navigation_html += '<a class="toTail_link" href="javascript:toTail()">Tail</a>';
    navigation_html += '<label>共'+ number_of_pages + '页</label>';
    $('#page_navigation').html(navigation_html);
    $('#page_navigation .page_link:first').addClass('active_page');

}
/**
 * 功能描述：跳转到前一页
 */
function previous(){
    new_page = parseInt($('#current_page').val()) - 1;
    if($('.active_page').prev('.page_link').length==true){
        go_to_page(new_page);
    }
}
/**
 * 功能描述：跳转到后一页
 */
function next(){
    new_page = parseInt($('#current_page').val()) + 1;
    //if there is an item after the current active link run the function
    if($('.active_page').next('.page_link').length==true){
        go_to_page(new_page);
    }
}
/**
 * 功能描述：跳转到指定页
 * @param page_num
 */
function go_to_page(page_num){

    //每次导航条进行跳转前依情况进行刷新操作
    refreshTableFoot(page_num);
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
        $(".toHead_link").attr('href',"javascript:toHead()");
        $(".toHead_link").css("color","black");
        $(".previous_link").attr('href',"javascript:previous()");
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
        $(".toTail_link").attr('href',"javascript:toTail()");//去掉a标签中的href属性
        $(".toTail_link").css("color","black");
        $(".next_link").attr('href',"javascript:next()");
        $(".next_link").css("color","black");
    }
    //控制色块的跳转
    var showPerPage = parseInt($('#show_per_page').val());
    $('.page_link[longdesc=' + page_num +']').addClass('active_page').siblings('.active_page').removeClass('active_page');
    $('#current_page').val(page_num);
    //移除原始的页面
    $("tbody").remove();
    //创建新的表页
    createTablePage(page_num,showPerPage);
}

/**
 * 功能描述：跳到起始页
 */
function toHead()
{
    go_to_page(0);
}
/**
 * 功能描述：跳到末尾页
 */
function toTail()
{
    var show_per_page = parseInt($("#show_per_page").val());
    var number_of_items = resultDataSet.dataSize;
    var number_of_pages = Math.ceil(number_of_items/show_per_page);
    go_to_page(number_of_pages-1);
}
/**
 * 功能描述：根据当前选中的页面依情况刷新表尾的导航栏
 * @param page_num
 */
function refreshTableFoot(page_num)
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
        var navigation_html = '<a class="toHead_link" href="javascript:toHead()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previous();">Prev</a>';
        var start_link = 0;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber -1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:next();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTail()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance >= originEnd && currentEnd != originEnd)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHead()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previous();">Prev</a>';
        var start_link = originEnd - (tableFootPagesNumber-1);
        currentStart = start_link;
        var end_link = originEnd;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:next();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTail()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num + expandDistance < originEnd && page_num + expandDistance > currentEnd && currentEnd != originEnd)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHead()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previous();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber - 1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:next();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTail()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
    else if(page_num - expandDistance > originStart && page_num-expandDistance < currentStart && currentStart != originStart)
    {
        $("#page_navigation").empty();
        //重新生成
        var navigation_html = '<a class="toHead_link" href="javascript:toHead()">Head</a>';
        navigation_html += '<a class="previous_link" href="javascript:previous();">Prev</a>';
        var start_link = page_num - expandDistance;
        currentStart = start_link;
        var end_link = start_link + tableFootPagesNumber - 1;
        currentEnd = end_link;
        while (end_link >= start_link) {
            navigation_html += '<a class="page_link" href="javascript:go_to_page(' + start_link + ')" longdesc="' + start_link + '">' + (start_link + 1) + '</a>';
            start_link++;
        }
        navigation_html += '<a class="next_link" href="javascript:next();">Next</a>';
        navigation_html += '<a class="toTail_link" href="javascript:toTail()">Tail</a>';
        navigation_html += '<label>共'+ number_of_pages + '页</label>';
        $('#page_navigation').html(navigation_html);
    }
}
function showCurrentPageContent(result)
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
            tbBody += "<tr class='" + trColor + "'>"
                + "<td>" + (('DIC_ID' in datas[i]) != false ? datas[i].DIC_ID : "--") + "</td>"
                + "<td>" + (('PARENT_ID' in datas[i]) != false ? datas[i].PARENT_ID : "--") + "</td>"
                + "<td>" + (('DIC_CH_NAME' in datas[i]) != false ? datas[i].DIC_CH_NAME : "--") + "</td>"
                + "<td>" + (('DIC_VALUE' in datas[i]) != false ? datas[i].DIC_VALUE : "--") + "</td>"
                + "<td>" + (('DIC_DESC' in datas[i]) != false ? datas[i].DIC_DESC : "--") + "</td>"
                + "<td><input type='button' value='修 改' onclick='updateDicValue(this)'></td>"
                + "</tr>";
            $("#dicConfigTable").append(tbBody);
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
function searchData()
{

    if($("#filter").val() == "")
    {
        alert("搜索关键字不可为空")
    }
    else{
        searchKey = $("#filter").val();
        //清除原来的表格内容和分页导航栏的内容
        $("tbody").remove();
        $("#page_navigation").empty();
        //依据搜索条件重新生成表格
        initTable();
    }
}
/**
 * 功能描述：点击修改按钮后更新单元格中显示的内容
 */
function updateDicValue(para)
{
    var cell = para.parentNode;
    $(cell).empty();
    var newCellContent = "<div><input type='text' class='newValue' placeholder='输入新的参数值...'>" +
        "<input type='button' value='确认' onclick='storeUpdateValue(this)'></div>";
    $(cell).html(newCellContent);

}
/**
 * 功能描述：将更新的值传到后台保存
 */
function storeUpdateValue(para)
{
    var pre = $(para).prev();
    var newValue = $(pre).val();
    if(newValue == "")
    {
        alert("修改值不能为空");
    }
    else
    {
        if(!isNaN(newValue)){
            //获取该行的参数id
            var cell = para.parentNode.parentNode;
            var allPrevTdNode = $(cell).prevAll();
            var dicId = $(allPrevTdNode[allPrevTdNode.length-1]).text();
            //传输修改后的值到后台
            $.ajax({
                async: false,
                catch: false,
                type: "post",
                dataType: "text",
                url: serverUrl + "updateDicValue.action?newDicValue=" + newValue +
                    "&dicId=" + dicId,
                error: function () {
                    alert("修改失败");
                },
                success: function (result) {
                    //将修改输入框中的内容清空
                    $(pre).val("");

                    if(result == "false")
                    {
                        alert("修改失败");
                    }
                    else
                    {
                        alert("修改成功");
                    }
                }
            });
        }else{
            alert("请输入数字类型的值");
        }
    }

}