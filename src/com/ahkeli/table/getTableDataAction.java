package com.ahkeli.table;

import com.ahkeli.dao.GetSqlSession;
import com.ahkeli.menu.UserPrivilegeData;
import com.ahkeli.model.TabelTestData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import io.netty.handler.codec.http.HttpResponse;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
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
 * Created by dell on 2015/11/24.
 */

public class getTableDataAction extends ActionSupport{
    private ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");

    public void getTableData() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //获取前端传来的参数
        String orderType =  request.getParameter("orderType") == null ? "" : request.getParameter("orderType").toString();
        String orderKey =  request.getParameter("orderKey")== null ? "" : request.getParameter("orderKey").toString();
        String pageNumber = request.getParameter("pageNumber")== null ? "" : request.getParameter("pageNumber").toString();
        String showPerPage = request.getParameter("showPerPage")== null ? "" : request.getParameter("showPerPage").toString();

        Map params = new HashMap();
        List<Map> test = null;
        //若未点击排序按钮则默认为升序排列
        if(orderType.equals("") || orderKey.equals(""))
        {
            orderType = "asc";
            orderKey = "data_id";
        }
        int recordStartIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage)+1;
        int recordEndIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage) + Integer.parseInt(showPerPage);
        params.put("recordStartIndex",recordStartIndex);
        params.put("recordEndIndex",recordEndIndex);
        params.put("orderType",orderType);
        params.put("orderKey",orderKey);
        test = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.TabelTestDataMapper.selectOnePage",params);

        //此处暂时将测试数据放入到list中
        /*Map data1 = new HashMap();
        Map data2 = new HashMap();
        Map data3 = new HashMap();
        Map data4 = new HashMap();
        Map data5 = new HashMap();

        data1.put("myid","1");
        data1.put("personName","person1");
        data1.put("personDes","this is persion1s des");
        data2.put("myid","2");
        data2.put("personName","person2");
        data2.put("personDes","this is persion2s des");
        data3.put("myid","3");
        data3.put("personName","person3");
        data3.put("personDes","this is persion3s des");
        data4.put("myid","4");
        data4.put("personName","person4");
        data4.put("personDes","this is persion4s des");
        data5.put("myid","5");
        data5.put("personName","person5");
        data5.put("personDes", "this is persion5s des");

        if(orderType != null && orderType.equals("1")) {
            test.add(data1);
            test.add(data2);
            test.add(data3);
            test.add(data4);
            test.add(data5);
        }
        else if(orderType != null && orderType.equals("0"))
        {
            test.add(data5);
            test.add(data4);
            test.add(data3);
            test.add(data2);
            test.add(data1);
        }
        else
        {
            test.add(data2);
            test.add(data3);
            test.add(data1);
            test.add(data5);
            test.add(data4);
        }*/
        JSONArray array = JSONArray.fromObject(test);
        String coreData = array.toString();
        //通过数据库查询全部数据记录的条数
        //int size = test.size();
        int size = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.TabelTestDataMapper.getDataSize");
        String result = "";
        result += "{\"dataSize\":" + size + ",\"orderType\":\""+ orderType + "\",\"orderKey\":\""+orderKey
                + "\",\"coreData\":";
        result += coreData;
        result += "}";
        //System.out.println(result);
        response.getWriter().print(result);
    }

    /*public static void main(String [] args) throws IOException {
        getTableDataAction data = new getTableDataAction();
        data.getTableData();
    }*/
}
