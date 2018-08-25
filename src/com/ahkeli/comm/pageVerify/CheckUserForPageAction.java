package com.ahkeli.comm.pageVerify;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/22.
 */
public class CheckUserForPageAction extends ActionSupport {
    public void check() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //防止用户非法访问当前页面
        String userNo = request.getParameter("userNo")==null?"":request.getParameter("userNo");
        //获得该页面的路径
        String funUrl = request.getParameter("funUrl")==null?"":request.getParameter("funUrl");
        HttpSession session = request.getSession(false);
        Map user = (Map) session.getAttribute("user");
        if(user == null || !(user.get("userNo").toString().equals(userNo)))
        {
            response.getWriter().print("fail");
        }
        else
        {
            //String currentUrl = null;
            //currentUrl = request.getRequestURI();
            boolean hasPrivilege = false;
            List<String> userFunUrl = (List<String>) user.get("userFunctionURL");
            for (int i = 0; i<userFunUrl.size(); i++)
            {
                String userPrivilegeUrl = (String)userFunUrl.get(i);
                if(funUrl.equals(userPrivilegeUrl))
                {
                    hasPrivilege = true;
                    break;
                }
            }
            if(!hasPrivilege)
            {
                /*try {
                    response.sendRedirect("http://127.0.0.1:8080/pages/index.jsp?userNo="+userNo);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                response.getWriter().print("fail");
            }
        }
    }
}

