package com.ahkeli.login;

import com.ahkeli.dao.GetSqlSession;
import com.ahkeli.menu.UserPrivilegeData;
import com.ahkeli.model.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/11/17.
 */
public class LoginAction extends ActionSupport{

    private String userName;
    private String password;

    /**
     * 功能描述：对用户的登录信息进行后台验证
     * @return
     */
    public void verify() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        String errorMessage = null;
        boolean userExistFlag = false;
        UserInfo theUser = null;
        try {
            //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
            UserPrivilegeData userPrivilegeData = new UserPrivilegeData();
            //初始化权限-用户-角色表内存信息
            //userPrivilegeData.getData();
            //List<UserInfo> userInfos = UserPrivilegeData.SYS_USER_INFO_LIST;

            //根据传入的userName到数据库中查询该用户
            theUser = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.UserInfoMapper.selectTheUserInfo",userName);

            if (theUser == null)
            {
                errorMessage = "sorry,you're not the system's user";
                response.getWriter().print(errorMessage);
            }
            else if(theUser.getUserStatus()==0 || theUser.getValidFlag() == 0)
            {
                errorMessage = "your account is locked or unvalid";
                response.getWriter().print(errorMessage);
            }
            else if(theUser.getIsNoPwd() == 0)
            {

                if(!verifyPassword(this.password,theUser.getUserPassword()))
                {
                    errorMessage = "wrong password";
                    response.getWriter().print(errorMessage);
                }
                else
                {
                    if(sessionSet(theUser,userPrivilegeData,ctx)) {
                        response.getWriter().print("success");
                    }
                    else {
                        response.getWriter().print("fail");
                    }
                }
            }
            else if(theUser.getIsNoPwd() == 1)
            {
                //该用户不需要密码即可登录
                if(sessionSet(theUser,userPrivilegeData,ctx)) {
                    response.getWriter().print("success");
                }
                else {
                    response.getWriter().print("fail");
                }
            }
            else
            {
                response.getWriter().print("fail");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.getWriter().print("fail");
        }
    }
    public boolean sessionSet(UserInfo userInfo,UserPrivilegeData userPrivilegeData,ActionContext ctx)
    {
        List<Map> privileges = userPrivilegeData.getUserPrivilegeByUserNo(userInfo.getUserNo());
        //Map session = ctx.getSession();
        //12.22修改
        //创建session
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        //获取privileges中的全部URL链接
        List<String> userFunURL = new ArrayList<String>();
        for (int i = 0; i<privileges.size(); i++)
        {
            String url = (String) privileges.get(i).get("linkedPage");
            if(url != null) {
                userFunURL.add((String) privileges.get(i).get("linkedPage"));
            }
        }
        Map userSessionInfo = new HashMap();
        userSessionInfo.put("isNoPassword",userInfo.getIsNoPwd());
        userSessionInfo.put("userNo",userInfo.getUserNo());
        userSessionInfo.put("userId",userInfo.getUserId());
        userSessionInfo.put("userFunctionURL",userFunURL);
        userSessionInfo.put("isLogin", true);
        String ip = IpUtils.getIpAddr((HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST));
        userSessionInfo.put("userIp",ip);
        //userSessionInfo.put("userIp",)
        //session处于服务器端，将会保存多个登录用户的信息
        //不允许重复登录的情况（指不同机器同一用户登录）
        if(0 == userInfo.getReloginFlag())
        {
            //Map user = (Map) session.get(userInfo.getUserNo());
            Map user = (Map)session.getAttribute("user");
            if(user == null)
            {
                //用户首次登陆
                //将该用户的个人信息及有权限的链接信息保存到session中
                //session.put(userInfo.getUserNo(),userSessionInfo);
                session.setAttribute("user",userSessionInfo);
                return true;
            }
            else
            {
                //判断是否仍旧是当前用户
                if(!userInfo.getUserNo().equals(user.get("userNo")))
                {
                    //若并非当前用户说明同一浏览器下另一用户登录
                    // 则修改session中保存的内容覆盖之前用户的登录信息
                    session.setAttribute("user",userSessionInfo);
                    return true;
                }
                //通过ip判断是否依旧是当前机器用户在使用
                if(user.get("userIp").equals(ip) && (Boolean)user.get("isLogin") == true)
                {
                    return true;
                }
                else
                {
                    //发生重复登录的情况
                    return false;
                }
            }

        }
        else if(1 == userInfo.getReloginFlag())
        {
            //允许重复登录的情况
            //Map user = (Map) session.get(userInfo.getUserNo());
            Map user = (Map)session.getAttribute("user");
            if(user == null)
            {
                //用户首次登陆
                //将用户的个人信息及有权限的链接信息保存到session中
                //session.put(userInfo.getUserNo(),userSessionInfo);
                session.setAttribute("user",userSessionInfo);
                return true;
            }
            else
            {
                //判断是否仍旧是当前用户
                if(!userInfo.getUserNo().equals(user.get("userNo")))
                {
                    //若并非当前用户说明同一浏览器下另一用户登录
                    // 则修改session中保存的内容覆盖之前用户的登录信息
                    session.setAttribute("user",userSessionInfo);
                    return true;
                }
                if((Boolean)user.get("isLogin") == true)
                {
                    //发生重复登录的情况
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean verifyPassword(String oldPassword,String storedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(oldPassword.getBytes("utf-8")));
        //System.out.println(newstr);
        if(newstr.equals(storedPassword))
            return true;
        else
            return false;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
