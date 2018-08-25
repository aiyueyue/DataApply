package com.ahkeli.table;

import com.ahkeli.dao.GetSqlSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/17.
 */
public class GetDicConfigTableDataAction extends ActionSupport{
    public void getData() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        response.setCharacterEncoding("UTF-8");
        //获取前端传来的参数
        String orderType =  request.getParameter("orderType") == null ? "" : request.getParameter("orderType").toString();
        String orderKey =  request.getParameter("orderKey")== null ? "" : request.getParameter("orderKey").toString();
        String pageNumber = request.getParameter("pageNumber")== null ? "" : request.getParameter("pageNumber").toString();
        String showPerPage = request.getParameter("showPerPage")== null ? "" : request.getParameter("showPerPage").toString();
        String searchKey = request.getParameter("searchKey")== null ? "" : request.getParameter("searchKey").toString();

        Map params = new HashMap();
        List<Map> test = null;
        int size = 0;
        //若未点击排序按钮则默认为升序排列
        if(orderType.equals("") || orderKey.equals(""))
        {
            orderType = "asc";
            orderKey = "dic_id";
        }
        int recordStartIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage)+1;
        int recordEndIndex = Integer.parseInt(pageNumber) * Integer.parseInt(showPerPage) + Integer.parseInt(showPerPage);
        params.put("recordStartIndex",recordStartIndex);
        params.put("recordEndIndex",recordEndIndex);
        params.put("orderType",orderType);
        params.put("orderKey",orderKey);
        //搜索条件为空的情况
        if(searchKey.equals("")) {
            test = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.GetDicConfigData.selectOnePage", params);
            //通过数据库查询全部数据记录的条数
            //int size = test.size();
            size = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.GetDicConfigData.getDataSize");
        }
        else
        {
            //存在搜索条件的情况
            searchKey = java.net.URLDecoder.decode(searchKey,"utf-8");
            params.put("searchKey",searchKey);
            test = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.GetDicConfigData.selectOnePageSearch", params);
            //通过数据库查询全部数据记录的条数
            //int size = test.size();
            try {
                size = GetSqlSession.sqlSession.selectOne("com.ahkeli.myBatisMapper.GetDicConfigData.getDataSizeSearch", params);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        JSONArray array = JSONArray.fromObject(test);
        String coreData = array.toString();
        String result = "";
        result += "{\"dataSize\":" + size + ",\"orderType\":\""+ orderType + "\",\"orderKey\":\""+orderKey
                + "\",\"coreData\":";
        result += coreData;
        result += "}";
        System.out.println(result);
        response.getWriter().print(result);
    }

    public void updateValue() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //获取前端传来的参数
        String newDicValue =  request.getParameter("newDicValue") == null ? "" : request.getParameter("newDicValue").toString();
        String dicId =  request.getParameter("dicId")== null ? "" : request.getParameter("dicId").toString();

        //进一步判断新值是否为数字,避免跳过js验证
        Boolean strResult = newDicValue.matches("-?[0-9]+.*[0-9]*");
        if(strResult == true)
        {
            if(dicId != "" )
            {
                //执行更新操作
                try
                {
                    Map params = new HashMap();
                    params.put("newDicValue",newDicValue);
                    params.put("dicId",dicId);
                    GetSqlSession.sqlSession.update("com.ahkeli.myBatisMapper.GetDicConfigData.updateDicValue",params);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    response.getWriter().print("false");
                }
            }
            else
            {
                response.getWriter().print("false");
            }
        }
        else
        {
            response.getWriter().print("false");
        }
    }
}
