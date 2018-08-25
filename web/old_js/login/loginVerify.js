/**
 * Created by pangbo on 2015/11/17.
 */
var serviceUrl = "http://192.168.7.234:8080/MapTest/";

function loginVerify()
{
    var userName = $("#userName").val();
    var password = $("#password").val();

    if(userName == "" && password == "")
    {
        $("#errorMessage").html("输入内容不可为空");
    }
    else
    {
        $("#errorMessage").html("");

        $.ajax({
            async : false,
            cache:false,
            type: 'POST',
            dataType : "text",
            url: serviceUrl + "Login.action?userName="+userName+"&password="+password,//请求的action路径
            error: function () {//请求失败处理函数
                alert('登录失败');
            },
            success:function(result)
            {   //请求成功后处理函数。
                if(result.valueOf() == "success")
                {
                    window.location.href = serviceUrl + "js/menu/menu.jsp";
                }
                else
                {
                    $("#errorMessage").text(result);
                }
            }
        });
    }
}