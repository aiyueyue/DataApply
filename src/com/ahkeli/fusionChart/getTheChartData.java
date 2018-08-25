package com.ahkeli.fusionChart;

import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/11/20.
 */
public class getTheChartData {
    private String chartType;

    public void getData() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        /*HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("chartType") == 1)*/
        if(this.chartType.equals("1")) {
            List list = new ArrayList();
            Map map = new HashMap();
            map.put("name", "movie1");
            map.put("count", 1100);
            Map map1 = new HashMap();
            map1.put("name", "movie2");
            map1.put("count", 901);
            Map map2 = new HashMap();
            map2.put("name", "movie3");
            map2.put("count", 41);
            list.add(map);
            list.add(map1);
            list.add(map2);

            JSONArray array = JSONArray.fromObject(list);
            System.out.println(array.toString());
            response.getWriter().print(array.toString());
        }
        else if(this.chartType.equals("2"))
        {
            List list = new ArrayList();
            Map map = new HashMap();
            map.put("name", "movie1");
            map.put("count", 1100);
            Map map1 = new HashMap();
            map1.put("name", "movie2");
            map1.put("count", 901);
            list.add(map);
            list.add(map1);

            JSONArray array = JSONArray.fromObject(list);
            System.out.println(array.toString());
            response.getWriter().print(array.toString());
        }
        else if(this.chartType.equals("3"))
        {
            List list = new ArrayList();
            Map map = new HashMap();
            map.put("name", "movie1");
            map.put("count", 1100);
            Map map1 = new HashMap();
            map1.put("name", "movie2");
            map1.put("count", 901);
            Map map2 = new HashMap();
            map2.put("name", "movie3");
            map2.put("count", 41);
            list.add(map);
            list.add(map1);
            list.add(map2);

            JSONArray array = JSONArray.fromObject(list);
            System.out.println(array.toString());
            response.getWriter().print(array.toString());
        }
        else if(this.chartType.equals("4"))
        {

        }
        else
        {}
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}
