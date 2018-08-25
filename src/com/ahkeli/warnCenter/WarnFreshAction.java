package com.ahkeli.warnCenter;

import com.ahkeli.dao.DBInfoDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/1/5.
 */
public class WarnFreshAction extends ActionSupport {
    public void warnFresh() throws IOException {
        ApplicationContext ac= new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
        DBInfoDao dbInfoDao= (DBInfoDao) ac.getBean("dbInfoDao");
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        response.setCharacterEncoding("UTF-8");

        String orderType =  request.getParameter("orderType") == null ? "" : request.getParameter("orderType").toString();
        String orderKey =  request.getParameter("orderKey")== null ? "" : request.getParameter("orderKey").toString();
        String pageNumber = request.getParameter("pageNumber")== null ? "" : request.getParameter("pageNumber").toString();
        String showPerPage = request.getParameter("showPerPage")== null ? "" : request.getParameter("showPerPage").toString();
        String startDate=request.getParameter("startTime")== null ? "" : request.getParameter("startTime").toString();
//        System.out.println("startDate=" + startDate);
        String dealDesc=request.getParameter("dealDesc")== null ? "" : request.getParameter("dealDesc").toString();
        String warnId=request.getParameter("warnId")== null ? "" : request.getParameter("warnId").toString();
        dealDesc=java.net.URLDecoder.decode(dealDesc,"utf-8");


        Map params = new HashMap();
        List<Map> test = null;
        //若未点击排序按钮则默认为升序排列
        if(orderType.equals("") || orderKey.equals(""))
        {
            orderType = "desc";
            orderKey = "WARN_ID";
        }
        int recordStartIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage)+1;
        int recordEndIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage) + Integer.parseInt(showPerPage);
        params.put("recordStartIndex",recordStartIndex);
        params.put("recordEndIndex",recordEndIndex);
        params.put("orderType",orderType);
        params.put("orderKey",orderKey);
//        test = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.WarnCenterDetailMapper.selectOnePage",params);
        int size=0;
        String bigTime="";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("dealDesc="+dealDesc+";warnId="+warnId);
        if(!dealDesc.equals("")&&!warnId.equals("")){
            System.out.println("wwwwwww");
            params.put("dealDesc",dealDesc);
            params.put("warnId",new BigDecimal(warnId));
            try {
                dbInfoDao.updateWarnHandle(params);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(startDate.equals("")){
            test = dbInfoDao.selectWarnFreshList1(params);
            size=dbInfoDao.selectWarnFreshSize1(params);
        }else{
            params.put("startDate",startDate);
            test=dbInfoDao.selectWarnFreshList(params);
            size=dbInfoDao.selectWarnFreshSize(params);
        }
        bigTime=df.format(dbInfoDao.selectWarnFreshBigTime());
//        System.out.println("startDate="+startDate+"..."+"size="+size);

        JSONArray array = JSONArray.fromObject(test);
        String coreData = array.toString();
        //通过数据库查询全部数据记录的条数
        //int size = test.size();
//        int size = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.GetDicConfigDataMapper.getDataSize");
        String result = "";
        result += "{\"dataSize\":" + size + ",\"orderType\":\""+ orderType + "\",\"orderKey\":\""+orderKey+ "\",\"bigTime\":\""+bigTime
                + "\",\"coreData\":";
        result += coreData;
        result += "}";
        System.out.println(result);
        response.getWriter().print(result);
    }
}
