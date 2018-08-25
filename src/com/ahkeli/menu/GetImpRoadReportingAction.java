package com.ahkeli.menu;

import com.ahkeli.dao.GetSqlSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by pangbo on 2015/12/14.
 */
public class GetImpRoadReportingAction extends ActionSupport{
    /**
     * 功能描述：获取重点路段页面中饼图和表格所需的数据
     */
    public void getData() throws IOException {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        try {
            //获取重要路段的sectionID
            //首先查询重点道路对应的路段id,一条重点道路可能对应多个路段id
            List<Map> sections = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.ImpRoadSection");
            List<String> sectionIds = new ArrayList<String>();
            int sectionSize = sections.size();
            for (int i = 0; i<sectionSize; i++)
            {
                //分割取到的sectionID, 以逗号分割
                String temp = (String)sections.get(i).get("SECTIONIDS");
                String [] tempIDS = temp.split("[,]");
                for (int j = 0; j<tempIDS.length; j++)
                {
                    sectionIds.add(tempIDS[j]);
                }
            }
            //设置数组保存出现重复的路段ID
            List<Integer> repeatIndex = new ArrayList<Integer>();
            for (int i = 0; i<sectionIds.size(); i++)
            {
                for (int j = i+1; j<sectionIds.size(); j++)
                {
                    if(sectionIds.get(i).equals(sectionIds.get(j)))
                    {
                        repeatIndex.add(i);
                    }
                }
            }
            //删除重复的路段ID
            for (int i = 0; i<repeatIndex.size(); i++)
            {
                sectionIds.remove(repeatIndex.get(i));
            }
            int subListSize = 500;
            //拆分的原因是sql中in关键字后面的列表至多不能超过1000个元素
            List<List<String>> subLists = createList(sectionIds, subListSize);
            //获取报表显示所需要的数据
            List<Map> currentPieChartData = new ArrayList<Map>();
            List<Map> currentTableData = new ArrayList<Map>();
            for (int i = 0; i<subLists.size(); i++)
            {
                List<Map> tempPieData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.getPieChartData",subLists.get(i));
                currentPieChartData.addAll(tempPieData);
                List<Map> tempTableData = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.getTableData",subLists.get(i));
                currentTableData.addAll(tempTableData);
            }
            List<Map> tableData = statisticTableData(currentTableData);
            List<Map> pieChartData = statisticPieChartData(currentPieChartData);
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

    /**
     * 功能描述：将一个大的list拆分为子list
     * @param targe
     * @param size
     * @return
     */
    public static List<List<String>>  createList(List<String> targe,int size) {
        List<List<String>> listArr = new ArrayList<List<String>>();
        //获取被拆分的数组个数
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
        for(int i=0;i<arrSize;i++) {
            List<String>  sub = new ArrayList<String>();
            //把指定索引数据放入到list中
            for(int j=i*size;j<=size*(i+1)-1;j++) {
                if(j<=targe.size()-1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    /**
     * 功能描述：统计重点路段中的TPI指数信息
     * @param source
     * @return
     */
    public List<Map> statisticPieChartData(List<Map> source)
    {
        List<Map> result = new ArrayList<Map>();
        //初始化result
        for (int i = 0; i<5; i++)
        {
            Map<String,Integer> initial = new HashMap<String, Integer>();
            initial.put("SECTION_TPI_STATUS",i+1);
            initial.put("COUNTNUM",0);
            result.add(initial);
        }
        for (int i = 0; i<source.size(); i++)
        {
            Map temp =  result.get(Integer.parseInt((source.get(i).get("SECTION_TPI_STATUS")).toString()) -1);
            temp.put("COUNTNUM",(Integer)temp.get("COUNTNUM")+1);
        }
        return result;
    }

    /**
     * 功能描述：对重点路段记录按TPI指数排序后取前20条数据
     * @param source
     * @return
     */
    public List<Map> statisticTableData(List<Map> source)
    {
        List<Map> result = new ArrayList<Map>();
        //降序排序
        Collections.sort(source, new Comparator<Map>() {
            public int compare(Map arg0, Map arg1) {
                return (arg1.get("SECTION_TPI").toString()).compareTo(arg0.get("SECTION_TPI").toString());
            }
        });
        //取出按tpi由高到低排序后的前20条数据
        if(source.size() > 20)
        {
            result.addAll(source.subList(0,20));
        }
        else
        {
            result.addAll(source);
        }
        return result;
    }
}
