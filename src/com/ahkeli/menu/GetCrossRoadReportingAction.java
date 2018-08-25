package com.ahkeli.menu;

import com.ahkeli.dao.GetSqlSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/15.
 */
public class GetCrossRoadReportingAction extends ActionSupport {
    public void getData()
    {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");

        try
        {
            //获取饼图显示所需要的数据
            List<Map> pieChartData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getCrossRoadData.getPieChartData");
            //获取表格显示所需要的数据
            List<Map> tableData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getCrossRoadData.getTableData");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
