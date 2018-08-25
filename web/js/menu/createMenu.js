/**
 * Created by pangbo on 2015/12/2.
 */

/**
 * 功能描述：用于生成权限菜单以及用户头像、用户最高角色信息
 */
$(document).ready(function(){
    var serviceUrl = "http://127.0.0.1:8080/";
    var nodeData = [];
    var userNo = $("#userNo").val();
    $.ajax({
        async: false,
        cache: false,
        type: 'POST',
        dataType: "json",
        url: serviceUrl + "getPrivilegeTree.action?userNo=" + userNo,
        error: function () {
            alert('请求失败');
        },
        success: function (data) {
            nodeData = data.nodeData;
            $(".font-bold").text(data.userNo);
            $(".text-muted").text(data.userRoleName);
            $(".text-muted").append("<b class=\"caret\"></b>");
            if(data.userIcon != 'null') {
                $(".img-circle").attr("src", data.userIcon);
            }
        }
    });
    //test data
    /*var nodeData =
        [{"id":2,"open":false,"pId":1,"name":"交通状态评价","isFunNode":false},{"id":3,"open":false,"pId":1,"name":"交通事件检测","isFunNode":false},{"id":4,"open":false,"pId":1,"name":"智能交通诱导","isFunNode":false},{"id":5,"open":false,"pId":1,"name":"交通信号控制","isFunNode":false},{"id":6,"open":false,"pId":1,"linkedPage":null,"name":"交通短时预测","isFunNode":true},{"id":7,"open":false,"pId":1,"linkedPage":null,"name":"原始数据判定","isFunNode":true},{"id":8,"open":false,"pId":1,"name":"系统配置管理","isFunNode":false},{"id":9,"open":false,"pId":1,"name":"预警监控通知","isFunNode":false},{"id":10,"open":false,"pId":2,"linkedPage":null,"name":"路段","isFunNode":true},{"id":11,"open":false,"pId":2,"linkedPage":null,"name":"重点道路","isFunNode":true},{"id":12,"open":false,"pId":2,"linkedPage":null,"name":"片区","isFunNode":true},{"id":13,"open":false,"pId":2,"name":"区域","isFunNode":false},{"id":14,"open":false,"pId":2,"linkedPage":null,"name":"全路网","isFunNode":true},{"id":15,"open":false,"pId":2,"linkedPage":null,"name":"交通建议","isFunNode":true},{"id":18,"open":false,"pId":3,"linkedPage":null,"name":"路口拥堵","isFunNode":true},{"id":19,"open":false,"pId":3,"linkedPage":null,"name":"路段拥堵","isFunNode":true},{"id":20,"open":false,"pId":4,"linkedPage":null,"name":"路径规划","isFunNode":true},{"id":21,"open":false,"pId":4,"linkedPage":null,"name":"诱导屏诱导","isFunNode":true},{"id":22,"open":false,"pId":5,"linkedPage":null,"name":"控制方式选择","isFunNode":true},{"id":23,"open":false,"pId":5,"linkedPage":null,"name":"控制效果评估","isFunNode":true},{"id":24,"open":false,"pId":8,"linkedPage":null,"name":"参数配置","isFunNode":true},{"id":25,"open":false,"pId":8,"linkedPage":null,"name":"基础配置","isFunNode":true},{"id":26,"open":false,"pId":8,"linkedPage":null,"name":"设备管理","isFunNode":true},{"id":27,"open":false,"pId":8,"linkedPage":null,"name":"过程管理","isFunNode":true},{"id":28,"open":false,"pId":8,"linkedPage":null,"name":"角色管理","isFunNode":true},{"id":29,"open":false,"pId":8,"linkedPage":null,"name":"用户管理","isFunNode":true},{"id":30,"open":false,"pId":8,"linkedPage":null,"name":"日志管理","isFunNode":true},{"id":31,"open":false,"pId":9,"linkedPage":null,"name":"通知配置","isFunNode":true},{"id":32,"open":false,"pId":9,"linkedPage":null,"name":"监控统计","isFunNode":true},{"id":33,"open":false,"pId":9,"linkedPage":null,"name":"监控明细","isFunNode":true},{"id":34,"open":false,"pId":9,"linkedPage":null,"name":"监控处理","isFunNode":true},{"id":35,"open":false,"pId":9,"linkedPage":null,"name":"监控级别","isFunNode":true},{"id":16,"open":false,"pId":13,"linkedPage":null,"name":"平峰","isFunNode":true},{"id":17,"open":false,"pId":13,"linkedPage":null,"name":"高峰","isFunNode":true}];
    */
    var queue=new Array();
    var rootNode = {"id":(-1),"open":false,"pId":0,"isFunNode":false};
    queue.unshift(rootNode);
    while(queue.length != 0)
    {
        var tempRoot = queue.pop();
        var rootId = tempRoot.id;
        //遍历找该根节点的孩子节点，将其添加到临时数组中，之后根据节点内容添加html
        var tempArray = new Array();
        for(var i = 0; i<nodeData.length; i++)
        {
            if(nodeData[i].pId == rootId)
            {
                tempArray.unshift(nodeData[i]);
            }
        }

        //将当前层次节点的html
        //需要分情况处理
        for (var j = tempArray.length-1; j>=0; j--)
        {
            var nodeHtml = "";
            //首批节点要添加到id为side-menu的ul元素中
            if(tempArray[j].pId == -1)
            {
                if(tempArray[j].isFunNode == false)
                {
                    nodeHtml += "<li><a href=\"index.html#\">";
                    nodeHtml += "<i class=\"fa fa-home\"></i>";
                    nodeHtml += "<span class=\"nav-label\">"+tempArray[j].name+"</span><span class=\"fa arrow\"></span></a>";
                    nodeHtml += "<ul id=\""+tempArray[j].id+"\""+ " class=\"nav nav-second-level\"></ul>";
                    nodeHtml += "</li>";
                    $("#side-menu").append(nodeHtml);
                }
                else
                {
                    nodeHtml += "<li><a class=\"J_menuItem\" href=\"" + tempArray[j].linkedPage + "\">";
                    nodeHtml += "<i class=\"fa fa-columns\"></i>";
                    nodeHtml += "<span class=\"nav-label\">"+tempArray[j].name+"</span></a></li>";
                    $("#side-menu").append(nodeHtml);
                }

            }
            else
            {
                var id = "#" + tempArray[j].pId;
                var nodeItem = $(id);
                if (tempArray[j].isFunNode == false) {
                    nodeHtml += "<li><a class=\"J_menuItem\" href=\"\">";
                    nodeHtml += tempArray[j].name+"<span class=\"fa arrow\"></span></a>";
                    nodeHtml += "<ul id=\""+tempArray[j].id+"\""+ " class=\"nav nav-third-level\"></ul>";
                    nodeHtml += "</li>";
                    nodeItem.append(nodeHtml);
                }
                else
                {
                    if(j == tempArray.length -1)
                    {
                        nodeHtml += "<li><a class=\"J_menuItem\" href=\""+ tempArray[j].linkedPage + "\" data-index=\"0\">"
                        nodeHtml += tempArray[j].name;
                        nodeHtml += "</a></li>";
                        nodeItem.append(nodeHtml);
                    }
                    else
                    {
                        nodeHtml += "<li><a class=\"J_menuItem\" href=\"" + tempArray[j].linkedPage + "\">"
                        nodeHtml += tempArray[j].name;
                        nodeHtml += "</a></li>";
                        nodeItem.append(nodeHtml);
                    }
                }
            }
        }
        //将临时数组中节点信息添加到队列中
        for (var z = tempArray.length-1; z>=0; z--)
        {
            queue.unshift(tempArray[z]);
        }
    }
})
