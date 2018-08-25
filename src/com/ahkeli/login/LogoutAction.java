package com.ahkeli.login;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/3.
 */
public class LogoutAction extends ActionSupport {
    /**
     * 功能描述：用于用户安全登出时session的销毁
     */
    public void logout()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession(false); //只获取session而不创建
        session.invalidate();
    }
}
