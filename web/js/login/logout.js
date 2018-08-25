/**
 * Created by pangbo on 2015/12/3.
 */
var serviceUrl = "http://127.0.0.1:8080/";
function logout()
{
    $.ajax({
        async:false,
        cache:false,
        type: 'post',
        url: serviceUrl + "Logout.action"
    });
    window.location.href = "login/login.jsp";
}