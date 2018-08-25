/**
 * Created by dell on 2015/12/4.
 */

//全局变量
var serviceUrl = "http://127.0.0.1:8080/";

/**
 * 功能描述：对修改密码的表单数据进行校验
 */
function changePasswordVerify()
{
    var oldPassword = $("#old").val();
    var newPassword = $("#new").val();
    var repeatPassword = $("#repeat").val();

    if(newPassword.length == 0 || repeatPassword.length == 0)
    {
        $("#errorMessage").text("存在空输入");
    }
    else if(newPassword != repeatPassword)
    {
        $("#errorMessage").text("重复密码与新密码不一致");
    }
    else if(newPassword.length > 25 || repeatPassword.length > 25)
    {
        $("#errorMessage").text("密码长度过长");
    }
    else
    {
        $("#errorMessage").text("");

        //开始发出后台验证请求
        $.ajax({
            async:false,
            catch:false,
            type:"post",
            dataType : "json",
            url: serviceUrl + "ChangePassword.action?oldPassword="+oldPassword
                  + "&newPassword="+newPassword + "&repeatPassword=" + repeatPassword,
            success:function(result){
                if(result[0].status == true)
                {
                    //layer弹出修改成功提示框
                    layer.msg("修改成功");
                }
                else
                {
                    $("#errorMessage").text(result[0].errorMessage);
                }
            },
            error:function(){
                $("#errorMessage").text("失败");
            }
        })
    }
}