package com.ahkeli.warnCenter;

import com.ahkeli.dao.DBInfoDao;
import com.ahkeli.model.RoleInfo;
import com.ahkeli.model.RoleWarnConfig;
import com.ahkeli.model.UserInfo;
import com.ahkeli.model.UserWarnConfig;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by wangliang on 2015/12/9.
 */
public class WarnConfigSaveAction extends ActionSupport implements ServletRequestAware {

     private HttpServletRequest request;
//     private String result;
//
//     public String getResult() {
//          return result;
//     }
//
//     public void setResult(String result) {
//          this.result = result;
//     }

     public void warnConfigSave(){
          String isSuccess="true";
          String reasond="null";
          ActionContext ctx = ActionContext.getContext();
          HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
          String roles=request.getParameter("roleInfos");
          String messages=request.getParameter("messageTypes");
          String[] roleInfos=roles.split("@");
          String[] messageTypes=messages.split("@");
          System.out.println(roles);
          ApplicationContext ac= new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
          DBInfoDao dbInfoDao=null;
          try {
               dbInfoDao = (DBInfoDao) ac.getBean("dbInfoDao");
               List<RoleInfo> roleList=new ArrayList<RoleInfo>();
               for(int i=0;i<roleInfos.length;i++){
                   System.out.println(roleInfos[i]);
                  RoleInfo roleInfo=dbInfoDao.selectByRoleName(roleInfos[i]);
                   System.out.println("拿到" + roleInfo);
                    for(int j=0;j<messageTypes.length;j++){
                      Map map=new HashMap<String,Objects>();
                      map.put("roleId", roleInfo.getRoleId());
                      map.put("messageType", Integer.parseInt(messageTypes[j]));
                        System.out.println(roleInfo.getRoleId()+".."+roleInfo.getRoleId().getClass() + "..." + messageTypes[j]+".."+messageTypes[j].getClass());
                      dbInfoDao.updateWarnRole(map);
                      RoleWarnConfig roleWarnConfig=new RoleWarnConfig();
                      roleWarnConfig.setRoleId(roleInfo.getRoleId());
                      roleWarnConfig.setMessageWay(Integer.parseInt(messageTypes[j]));
                      roleWarnConfig.setRecordUser("aaaa");
                      roleWarnConfig.setIsValid(1);
                      dbInfoDao.insertWarnRole(roleWarnConfig);
               }

          }
          }catch(Exception e){
               isSuccess="false";
               e.printStackTrace();
               reasond=e.getMessage();
          }
          String result = "";
          result += "{\"isSuccess\":" + isSuccess+",\"reason\":"+ reasond ;
          result += "}";
          System.out.println(result);
          try {
               response.getWriter().print(result);
          } catch (IOException e) {
               e.printStackTrace();
          }

     }


    public void warnConfigSave2(){
        String isSuccess="true";
        String reasond="null";
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        String users=request.getParameter("userInfos");
        String messages=request.getParameter("messageTypes");
        String[] userInfoIds=users.split("@");
        String[] messageTypes=messages.split("@");
        ApplicationContext ac= new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
        DBInfoDao dbInfoDao=null;
        try {
            dbInfoDao = (DBInfoDao) ac.getBean("dbInfoDao");
            List<UserInfo> roleList=new ArrayList<UserInfo>();
            for(int i=0;i<userInfoIds.length;i++){
                System.out.println(userInfoIds[i]);
//                RoleInfo roleInfo=dbInfoDao.selectByRoleName(userInfoIds[i]);
//                System.out.println("拿到" + roleInfo);
                UserInfo userInfo=dbInfoDao.selectUserByNo(userInfoIds[i]);
                for(int j=0;j<messageTypes.length;j++){
                    Map map=new HashMap<String,String>();
                    map.put("userId", userInfo.getUserId());
                    map.put("messageType", messageTypes[j]);
                    dbInfoDao.updateWarnUser(map);
                    UserWarnConfig userWarnConfig=new UserWarnConfig();
                    userWarnConfig.setUserId(userInfo.getUserId());
                    userWarnConfig.setMessageWay(Integer.parseInt(messageTypes[j]));
                    userWarnConfig.setRecordUser("aaaa");
                    userWarnConfig.setIsValid(1);
                    dbInfoDao.insertWarnUser(userWarnConfig);
                }

            }
        }catch(Exception e){
            isSuccess="false";
            e.printStackTrace();
            reasond=e.getMessage();
        }
        String result = "";
        result += "{\"isSuccess\":" + isSuccess+",\"reason\":"+ reasond ;
        result += "}";
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     @Override
     public void setServletRequest(HttpServletRequest httpServletRequest) {
          this.request=httpServletRequest;
     }
}
