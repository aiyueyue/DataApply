package com.ahkeli.menu;

import com.ahkeli.dao.GetSqlSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/14.
 */
public class GetCrossRoadInfoAction extends ActionSupport {
    public void getCrossRoadInfo()
    {
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");

        try
        {
            //开始获取交叉口的信息
            List<Map> crossInfo = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getCrossRoadData.getCrossInfo");
            //获取路口id及路口id对应的路段id
            List<Map> crossSectionInfo = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getCrossRoadData.getCrossSectionId");
            //获取每个路口对应路段的详细信息
            for (int i = 0; i<crossSectionInfo.size(); i++)
            {
                //拆分路段字符串
                String temp = (String)crossSectionInfo.get(i).get("ENTERSECTIONIDS");
                String [] tempIDS = temp.split("[;,]");
                List<String> sectionIds = new ArrayList<String>();
                for (int j = 0; j<tempIDS.length; j++)
                {
                    sectionIds.add(tempIDS[j]);
                }
                //根据拆分得到的路段id查询路段的详细信息
                List<Map> sectionInfos = GetSqlSession.sqlSession.selectList("com.ahkeli.myBatisMapper.getCrossRoadData.getCrossSectionInfo",sectionIds);
                crossSectionInfo.get(i).put("sectionInfos",sectionInfos);
            }
            //将查询到的交叉口对应的路段信息放入到交叉口信息列表中
            for (int i = 0; i<crossInfo.size(); i++)
            {
                String crossId = crossInfo.get(i).get("INTERSECTION_ID").toString();
                for (int j = 0; j<crossSectionInfo.size(); j++)
                {
                    String aidId = crossSectionInfo.get(j).get("AIDID").toString();
                    if(crossId.equals(aidId))
                    {
                        crossInfo.get(i).put("sectionInfos",crossSectionInfo.get(j).get("sectionInfos"));
                        break;
                    }
                }
            }
            /*String test = JSONArray.fromObject(crossSectionInfo).toString();
            System.out.println(test);*/
            String result = "";
            result += "{\"crossMapInfo\":";
            result += (crossInfo != null ? JSONArray.fromObject(crossInfo).toString(): "[]");
            /*result += ",\"crossSectionInfo\":";
            result += (crossSectionInfo != null ? JSONArray.fromObject(crossSectionInfo).toString() : "[]");*/
            result += "}";
            //test
            System.out.println(result);
            //
            response.getWriter().print(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
