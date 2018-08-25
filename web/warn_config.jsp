<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<%
    String showPages = request.getAttribute("showPage").toString();
    String showJumps = request.getAttribute("showJump").toString();
    System.out.println("showPage:" + showPages);
    System.out.println("showJump:" + showJumps);
    int showPage = Integer.parseInt(showPages);
    int showJump = Integer.parseInt(showJumps);
%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>通知配置</title>

    <link href="css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=3.2.0" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <form name="warnConfig" action="warnConfig" method="POST">
                    <input type="hidden" id="showPage" value="<%=showPage%>"/>
                    <input type="hidden" id="showJump" value="<%=showJump%>">

                    <div class="ibox-title">
                        <h5 style="color: #006621;text-align: center ">通知配置</h5>
                    </div>

                        <% if (showPage == 0) {%>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-5 m-b-xs">
                                <select id="selectWarnType" onchange="changeWay()"
                                        class="input-sm form-control input-s-sm inline" style="height: 45px">
                                    <option value="role" selected>按角色</option>
                                    <option value="user">按用户</option>
                                </select>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped" align="center">
                                <tr>
                                    <td>角色</td>
                                    <s:iterator value="roleList" id="roles" var="roleInfo">
                                        <td><input type="checkbox" name="roleInfo" class="i-checks"
                                                   value="<s:property value="#roleInfo.roleName"/>"><s:property
                                                value="#roleInfo.roleName"/></td>
                                    </s:iterator>
                                </tr>
                                <tr>
                                    <td>通知方式</td>
                                    <td><input type="checkbox" name="messageType" value="1"> 短信</td>
                                    <td><input type="checkbox" name="messageType" value="2">微信</td>
                                    <td><input type="checkbox" name="messageType" value="3">邮箱</td>
                                </tr>
                            </table>
                        </div>
                        <div style="margin-left: auto;margin-right: auto;width: 50%;position: relative">
                            <input type="button" value="提交" id="saveRoleConfig" style="position:relative;left: 53%" >
                        </div>
                        <div id="saveRole" style="color: red;width: 50%;float: right"></div>
                    </div>
                        <%
                        } else if (showPage == 1) {
                        %>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-5 m-b-xs">
                                <select id="selectWarnType1" onchange="changeWay1()"
                                        class="input-sm form-control input-s-sm inline">
                                    <option value="role">按角色</option>
                                    <option value="user" selected>按用户</option>
                                </select>
                            </div>
                            <div class="col-sm-3" style="float: right" style="float: right">
                                <div class="input-group" style="margin-right: 0px">
                                    <input type="text" placeholder="请输入用户ID或用户名" class="input-sm form-control"
                                           id="searchUserInfo"> <span class="input-group-btn">
               <button type="button" class="btn btn-sm btn-primary" id="searchUser" onclick="searchUsers()"> 搜索</button> </span>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th style="width: 40%">
                                        <div class="col-sm-4 m-b-xs">
                                            <div data-toggle="buttons" class="btn-group">
                                                <label class="btn btn-sm btn-white">
                                                    <input type="radio" id="selectAll" name="options">全选</label>
                                                <label class="btn btn-sm btn-white ">
                                                    <input type="radio" id="removeAll" name="options">取消全选</label>
                                            </div>
                                        </div>
                                    </th>
                                    <th style="width: 30%">用户编号</th>
                                    <th style="width: 30%">用户名</th>
                                </tr>
                                </thead>
                                <tbody>
                                <s:iterator value="#request.pageBean.userList" id="userInfo" var="userInfo">
                                    <tr>
                                        <th><input type="checkbox" name="userInfo"
                                                   value="<s:property value="#userInfo.userNo"/>"></th>
                                        <th><s:property value="#userInfo.userNo"/></th>
                                        <th><s:property value="#userInfo.userName"/></th>
                                    </tr>
                                </s:iterator>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <td>通知方式</td>
                                    <td><input type="checkbox" name="messageType2" value="1"> 短信</td>
                                    <td><input type="checkbox" name="messageType2" value="2">微信</td>
                                    <td><input type="checkbox" name="messageType2" value="3">邮箱</td>
                                </tr>
                            </table>
                            <div class="pagination pull-right">
                                <% if (showJump != 1) { %>
                                <a>共</a><a style="color: red"><s:property
                                    value="#request.pageBean.totalPage"/></a><a>页</a>
                                <a>共</a><a style="color:red"><s:property
                                    value="#request.pageBean.allRows"/> </a><a>条记录</a>
                                <s:if test="#request.pageBean.currentPage==1">
                                    首页 &nbsp;&nbsp;&nbsp;上一页
                                </s:if>
                                <s:else>
                                    <a href="warnConfig.action">首页</a>
                                    &nbsp;&nbsp;&nbsp;
                                    <a href="warnConfig.action?page=<s:property value="#request.pageBean.currentPage-1"/>&showPage=1 ">上一页</a>
                                </s:else>
                                <s:if test="#request.pageBean.currentPage!=#request.pageBean.totalPage">
                                    <a href="warnConfig.action?page=<s:property value="#request.pageBean.currentPage+1"/>&showPage=1 ">下一页</a>
                                    &nbsp;&nbsp;&nbsp;
                                    <a href="warnConfig.action?page=<s:property value="#request.pageBean.totalPage"/>&showPage=1 ">尾页</a>
                                </s:if>
                                <s:else>
                                    下一页&nbsp;&nbsp;&nbsp;尾页
                                </s:else>
                                <a>跳转至</a>
                                <input type="text" size="2" name="page">页
                                <input type="button" value="跳转" onclick="validate2()">
                            </div>
                                <%
                                } else if (showJump == 1) {

                                %>
                             <div style="margin-left: auto;margin-right: auto;width: 50%;position: relative">
                                <input type="button" value="返回" onclick="searchReturn()"  style="position:relative;left: 53%">
                                <%
                                    }
                                %>
                                <input type="button" value="提交" id="saveUserConfig" style="position:relative;left: 53%" >
                            </div>
                        </div>
                        <div id="saveUser" style="color: red;width: 50%;float: right"></div>
                    </div>
                        <%
                            }
                        %>
            </div>
        </div>
        </form>
    </div>
</div>
</div>
</div>
</body>
</html>

<script type="text/javascript">

    //改变显示界面
    function changeWay() {
        var selectWay = document.getElementById("selectWarnType").value;
        if (selectWay == "role") {
            document.getElementById("showPage").value = 0;
            document.forms["warnConfig"].action = "warnConfig.action?showPage=0";
            document.forms["warnConfig"].submit();
        } else if (selectWay == "user") {
            document.getElementById("showPage").value = 1;
            document.forms["warnConfig"].action = "warnConfig.action?showPage=1";
            document.forms["warnConfig"].submit();
        }
    }
    function changeWay1() {
        var selectWay = document.getElementById("selectWarnType1").value;
        if (selectWay == "role") {
            document.getElementById("showPage").value = 0;
            document.forms["warnConfig"].action = "warnConfig.action?showPage=0";
            document.forms["warnConfig"].submit();
        } else if (selectWay == "user") {
            document.getElementById("showPage").value = 1;
            document.forms["warnConfig"].action = "warnConfig.action?showPage=1";
            document.forms["warnConfig"].submit();
        }
    }
    function searchReturn() {
        document.forms["warnConfig"].action = "warnConfig.action?showPage=1";
        document.forms["warnConfig"].submit();
    }
    function validate2() {
        alert("aaaaaa");
        var page = document.getElementsByName("page")[0].value;
        if (page ><s:property value="#request.pageBean.totalPage"/>) {
            alert("你输入的页数大于最大页数，页面将跳转到首页");
            window.document.location.href = "warnConfig.action?showPage=1";
        }
    }

    function searchUsers() {
        var search = $("#searchUserInfo").val();
        alert(search);
        if (search == "") {
            alert("没有找到");
        } else {
            window.document.location.href = "searchUser.action?search=" + encodeURI(encodeURI(search)) + "&showPage=1";
        }
    }

    function saveRole() {
        document.forms["warnConfig"].action = "warnConfigRoleSave.action";
        document.forms["warnConfig"].submit();

    }


    $(document).ready(function () {
        $("#saveRoleConfig").click(function () {
            var roleInfos = [];
            $("input[name='roleInfo']:checked").each(function () {
                roleInfos.push($(this).val());
            });
            var messageTypes = [];
            $("input[name='messageType']:checked").each(function () {
                messageTypes.push($(this).val());
            });
            if (roleInfos == 0) {
                alert("没有选中角色");
            } else if (messageTypes == 0) {
                alert("没有选择通知方式");
            } else {
                var roleInfo = roleInfos.join('@');
                var messageType = messageTypes.join('@');
                $.ajax({
                    type: 'post',
                    url: "warnConfigRoleSave.action",
                    data: {
                        roleInfos: roleInfo,
                        messageTypes: messageType
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.isSuccess == true) {
                            alert("成功");
                            $("#saveRole").text("修改成功")
                        } else {
                            $("#saveRole").text("修改失败" + result.reasond)
                            alert("失败");
                        }
                    }

                })
            }
        })

        $("#selectAll").click(function () {
            $("[name='userInfo']").prop("checked", true);
        })

        $("#removeAll").click(function () {
            $("[name='userInfo']").prop("checked", false);
        })

//        $("#searchUser").click(function(){
//            var search=$("#searchUserInfo").val();
//            alert(search);
//            if(search!=null){
//                $.ajax({
//                    type:'post',
//                    url:"searchUser.action?search="+ encodeURI(encodeURI(search))+"&showPage=1",
//                    contentType:"application/x-www-form-urlencoded:charset=UTF-8",
//                })
//            }
//        })
    })


    $(document).ready(function () {
        $("#saveUserConfig").click(function () {
            var userInfos = [];
            $("input[name='userInfo']:checked").each(function () {
                userInfos.push($(this).val());
            });
            var messageTypes = [];
            $("input[name='messageType2']:checked").each(function () {
                messageTypes.push($(this).val());
            });
            if (userInfos == 0) {
                alert("没有选择用户");
            } else if (messageTypes == 0) {
                alert("没有选择通知方式");
            } else {
                var userInfoIds = userInfos.join('@');
                var messageTypes2 = messageTypes.join('@');
                $.ajax({
                    type: 'post',
                    url: "warnConfigUserSave.action",
                    data: {
                        userInfos: userInfoIds,
                        messageTypes: messageTypes2
                    },
                    dataType: "json",
                    success: function (result) {
                        if (result.isSuccess == true) {
                            $("#saveUser").text("修改成功")
                        } else {
                            $("#saveUser").text("修改失败" + result.reasond)
                        }
                    }

                })
            }
        })
    })


</script>