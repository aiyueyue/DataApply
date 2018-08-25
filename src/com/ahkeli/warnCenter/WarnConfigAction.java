package com.ahkeli.warnCenter;

import com.ahkeli.dao.DBInfoDao;
import com.ahkeli.model.RoleInfo;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangliang on 2015/12/7.
 */
public class WarnConfigAction extends ActionSupport {
    private List<RoleInfo> roleList;

    private UserService userService=new UserService();
    private int page;
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");


    @Override
    public String execute(){
        DBInfoDao dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
        roleList=new ArrayList<RoleInfo>();
        try {
            roleList = dbInfoDao.selectAllRole();
        }catch (Exception e){
            e.printStackTrace();
        }
        PageBean pageBean=userService.getPageBean(7,page);
        HttpServletRequest request= ServletActionContext.getRequest();
        String showPage=(request.getParameter("showPage")==null)?"0":request.getParameter("showPage").toString();
        String showJump=(request.getParameter("showJump")==null)?"0":request.getParameter("showJump").toString();
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("showPage",showPage);
        request.setAttribute("showJump",showJump);
        System.out.println(roleList.size());
        return "success";
    }

//    public String searchUser(){
//        DBInfoDao dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
//        PageBean pageBean=userService.getPageBean(7,page);
//        HttpServletRequest request=ServletActionContext.getRequest();
//        String showPage=(request.getParameter("showPage")==null)?"0":request.getParameter("showPage").toString();
//        List<UserInfo> userInfos=new ArrayList<UserInfo>();
//        String search=request.getParameter("search");
//        System.out.println("search:" + search);
//        try {
//            search= java.net.URLDecoder.decode(search, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        UserInfo userInfo=new UserInfo();
//        userInfo=dbInfoDao.selectUserByNo(search);
//        if(userInfo!=null) {
//            userInfos.add(userInfo);
//        }else {
//            userInfos = dbInfoDao.selectUserByName(search);
//        }
//        pageBean.setUserList(userInfos);
////        System.out.println("查到："+userInfos.get(0).getUserName());
//        System.out.println(pageBean.getUserList().size());
//        request.setAttribute("pageBean",pageBean);
//        request.setAttribute("showPage", showPage);
//        return "success";
//    }

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
}
