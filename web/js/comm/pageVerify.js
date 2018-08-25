/**
 * Created by dell on 2015/12/22.
 */
/**
 * 功能描述：用于向后台传送所需的当前登录用户信息
 */
function pageVerify()
{
    var userNo = $("#userNo",parent.document).val(); //来自index.jsp的隐藏变量
    var funUrl = window.location.pathname;
    $.ajax({
        async:false,
        catch:false,
        type:"post",
        dataType:"text",
        url: "pageVerify.action?userNo=" + userNo + "&funUrl="+funUrl,
        success:function(result)
        {
            if(result == "fail")
            {
                window.location.href = "/pages/login/login.jsp";
            }
        }
    });
}