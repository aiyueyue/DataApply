package com.ahkeli.menu;

import com.ahkeli.menu.UserPrivilegeData;
import com.ahkeli.model.PrivilegeInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/11/15.
 */

public class GetPrivilegeTreeAction extends ActionSupport {

    public void doGetPrivilegeTree() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //此处加入通过service类获取菜单数据的代码
        //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
        UserPrivilegeData userPrivilegeData = new UserPrivilegeData();
        //用户名由用户登录后保存在session中
        //获取从createMenu.js中传来的用户账号
        //修改：12.21
        String userNo = request.getParameter("userNo") == null ? "" : request.getParameter("userNo").toString();
        /*Map session = ctx.getSession();
        Map user = (Map)session.get(userNo);
        String userNo = (String)user.get("userNo");*/
        //获取用户菜单节点信息
        List<Map> listTree = userPrivilegeData.getUserPrivilegeByUserNo(userNo);
        //获取用户最高级别的角色名称
        Map userRoleAndIcon = userPrivilegeData.getUserRoleAndIcon(userNo);
        //测试时暂时使用固定的用户名：user1
        //List<Map> listTree = userPrivilegeData.getUserPrivilegeByUserNo("user1");
        //使用String字符串传输树形菜单
        /*String s1 = "{id:1, pId:0, name:\"test1\" , open:true}";
        String s2 = "{id:2, pId:1, name:\"test2\" , open:true}";
        String s5 = "{id:5, pId:1, name:\"test\" , open:true}";
        String s3 = "{id:3, pId:1, name:\"test3\" , open:true}";
        String s4 = "{id:4, pId:2, name:\"test4\" , open:true}";
        List<String> lstTree = new ArrayList<String>();
        //同一级下子菜单的显示顺序和其添加到list中的顺序有关
        lstTree.add(s5);
        lstTree.add(s1);
        lstTree.add(s2);
        lstTree.add(s3);
        lstTree.add(s4);*/
        //使用Map传输菜单数据
        /*List<Map> lstTree = new ArrayList<Map>();
        Map<String,Object> map1 = new HashMap<String, Object>();
        map1.put("id",new Integer(1));
        map1.put("pId",0);
        map1.put("name",new String("test1"));
        map1.put("open",true);
        map1.put("url","http://www.baidu.com");
        lstTree.add(map1);*/
        //利用Json插件将Array转换成Json格式
        //System.out.println(JSONArray.fromObject(listTree).toString());
        String nodeData = JSONArray.fromObject(listTree).toString();
        String result = "";
        result += "{\"userNo\":\""+userNo + "\",";
        result += "\"userIcon\":\"" + userRoleAndIcon.get("userIcon") + "\",";
        result += "\"userRoleName\":\""+userRoleAndIcon.get("userRole") + "\",";
        result += "\"nodeData\":";
        result += nodeData;
        result += "}";
        response.setCharacterEncoding("utf-8");
        System.out.println(result);
        response.getWriter().print(result);
    }
}
