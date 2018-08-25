package com.ahkeli.menu;

import com.ahkeli.comm.util.map.PointF;
import com.ahkeli.comm.util.map.PointLineOffset;
import com.ahkeli.comm.util.map.SLDUtils;
import com.ahkeli.comm.util.map.WebMercator;
import com.ahkeli.dao.GetSqlSession;
import org.apache.ibatis.session.SqlSession;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/11.
 */
public class GetImpRoadImgData {
    private SqlSession sqlSession;

    public GetImpRoadImgData()
    {
        this.sqlSession = GetSqlSession.sqlSession;
    }
    public List<Map> getImpRoadSslkList()
    {
        //首先查询重点道路对应的路段id,一条重点道路可能对应多个路段id
        List<Map> sections = sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.ImpRoadSection");
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
        List<List<String>> subLists = createList(sectionIds,subListSize);
        List<Map> result = new ArrayList<Map>();
        for (int i = 0; i<subLists.size(); i++)
        {
            List<Map> temp = sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.drawImpRoadMapData",subLists.get(i));
            result.addAll(temp);
        }

        return result;
        //return sqlSession.selectList("com.ahkeli.myBatisMapper.getImpRoadPageData.drawImpRoadMapData",sectionIds);
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
    public BufferedImage getJtllImage(float centerX, float centerY,
                                      int zoom, int width, int height,String mapType, List lkList)
    {
        //GIF透明色-----------
        //        IndexColorModel cm = createIndexColorModel();;
        //        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, cm);
        //        Graphics2D graphics = image.createGraphics();
        //--------------------

        //PNG透明色-----------
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        //透明背景色
        image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        graphics.dispose();
        graphics = image.createGraphics();
        //--------------------
        //去锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //graphics.setBackground(Color.black);
        graphics.setStroke(new BasicStroke(1.0f));

        //计算偏移量
        WebMercator wm;
        int iPixelOffset = 0;
        if(mapType.equals("arcgisUtil")){// arcgis地图
            /*iPixelOffset = (int)(zoom/3)+3;
            wm = new WebMercatorForArcgis();*/
            wm = null;
        }else{							// google地图
            iPixelOffset = (int)(zoom/3);
            wm = new WebMercator();
        }
        Point centerPositionInPixelCS = wm.fromMapPointToPixelPoint(centerX, centerY, zoom);
        Point pointOffsetAboutPixelAndScreenCS = new Point();
        pointOffsetAboutPixelAndScreenCS.x = width / 2 - centerPositionInPixelCS.x;
        pointOffsetAboutPixelAndScreenCS.y = height / 2 - centerPositionInPixelCS.y;

        //画路况
//		Map sslkMap = TopBaseAction.getJtllBean().getSslkPo().getSslkMap();
//		List lkList = (List)sslkMap.get("lkList");
        List pointList = new ArrayList();
        List points = new ArrayList();
        try {
            for (int i = 0; i < lkList.size(); i++)
            {
                //road = {ROADID=1486, COLOR=0xFF0000, ROADTYPE=6, ROADNAME=阜南路(六安路-阜阳路), SPEED=4, STATUS=5, XY=117.277507,31.872487,117.280083,31.872143,117.281162,31.871999, LC=246.0, HS=4}
                Map road = (Map) lkList.get(i);
                //等级过滤(合肥)
			/*if(zoom<15 && (road.get("ROADTYPE").toString().equals("8") || road.get("ROADTYPE").toString().equals("7"))){
				continue;
			}
			if(zoom<17 && road.get("ROADTYPE").toString().equals("8")){
				continue;
			}*/

                String[] xyArr = road.get("XY").toString().split(",");
                if (xyArr.length < 4) continue;
//			PointLineOffset.points.clear();
                pointList.clear();
                points.clear();
                for (int j = xyArr.length - 1; j >= 0; j = j - 2) {
                    Point p = wm.fromMapPointToPixelPoint(Double.parseDouble(xyArr[j - 1]), Double.parseDouble(xyArr[j]), zoom);
                    float x = p.x + pointOffsetAboutPixelAndScreenCS.x;
                    float y = p.y + pointOffsetAboutPixelAndScreenCS.y;
//				PointLineOffset.points.add(new PointF(x, y));
                    pointList.add(new PointF(x, y));
                    points.add(new PointF(x, y));
                }
                if (points.size() != pointList.size())
                    continue;
                List offsetPointList = PointLineOffset.getOneSideBuffer(iPixelOffset, points);
                for (int k = offsetPointList.size() - 1; k >= 0; k--) {
                    pointList.add(offsetPointList.get(k));
                }

                Polygon polygon = SLDUtils.getPolygon(pointList);
                graphics.setColor(new Color(Integer.parseInt(road.get("COLOR").toString().substring(2), 16)));
                graphics.fillPolygon(polygon);
                graphics.setColor(new Color(0xFFFFFF));
                graphics.drawPolygon(polygon);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        graphics.dispose();
        return image;
    }
}
