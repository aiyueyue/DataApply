package com.ahkeli.menu;

import com.ahkeli.dao.GetSqlSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/12/8.
 */
public class GetSectionDataAction extends ActionSupport {
    /**
     * 功能描述：获取路段页面中饼图和表格所需的数据
     */
    public void getData() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        try {
            //获取饼图显示所需要的数据
            List<Map> pieChartData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getSectionData.selectPieChartData");
            //获取表格显示所需要的数据
            List<Map> tableData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getSectionData.getTableData");
            String result = "";
            result += "{\"tableData\":";
            if(tableData == null)
            {
                result += "[]";
            }
            else {
                result += JSONArray.fromObject(tableData).toString();
            }
            result += ",\"pieChartData\":";
            if(pieChartData == null)
            {
                result += "[]";
            }
            else {
                result += JSONArray.fromObject(pieChartData).toString();
            }
            result += "}";
            System.out.println(result);
            response.getWriter().print(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

