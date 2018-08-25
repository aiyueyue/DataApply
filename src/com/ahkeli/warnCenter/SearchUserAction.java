package com.ahkeli.warnCenter;

import com.ahkeli.dao.DBInfoDao;
import com.ahkeli.model.RoleInfo;
import com.ahkeli.model.UserInfo;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliang on 2015/12/15.
 */
public class SearchUserAction extends ActionSupport {

    private List<RoleInfo> roleList;

    private UserService userService=new UserService();
    private int page;
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");

    public String searchUser(){
        DBInfoDao dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
        PageBean pageBean=userService.getPageBean(7,page);
        HttpServletRequest request= ServletActionContext.getRequest();
        String showPage=(request.getParameter("showPage")==null)?"0":request.getParameter("showPage").toString();
        String showJump="1";
        List<UserInfo> userInfos=new ArrayList<UserInfo>();
        String search=request.getParameter("search");
        System.out.println("search:" + search);
        try{
            search= java.net.URLDecoder.decode(search,"utf-8");
            UserInfo userInfo=new UserInfo();
            userInfo=dbInfoDao.selectUserByNo(search);
            userInfos.add(userInfo);
        }catch(Exception e){
            userInfos=dbInfoDao.selectUserByName(search);
        }
        pageBean.setUserList(userInfos);
//        System.out.println("查到："+userInfos.get(0).getUserName());
        System.out.println(pageBean.getUserList().size());
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("showPage", showPage);
        request.setAttribute("showJump",showJump);
        return "success";
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

//    public void searchUser(){
//        ActionContext ctx = ActionContext.getContext();
//        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
//        HttpServletRequest request= ServletActionContext.getRequest();
//        String search=request.getParameter("search");
//        ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
//        DBInfoDao dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
//        List<UserInfo> userInfos=new ArrayList<UserInfo>();
//        try{
//            short userId= (short) Integer.parseInt(search);
//            UserInfo userInfo=new UserInfo();
//            userInfo=dbInfoDao.selectUserById(userId);
//           userInfos.add(userInfo);
//        }catch(Exception e){
//            userInfos=dbInfoDao.selectUserByName(search);
//        }
//        String result = "";
//        result += "{\"userInfos2\":" + JSONArray.fromObject(userInfos).toString() ;
//        result += "}";
//        System.out.println(result);
//        try {
//            response.getWriter().print(result);
//            System.out.println("aaaaaaaaa");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
