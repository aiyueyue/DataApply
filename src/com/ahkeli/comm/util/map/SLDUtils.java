package com.ahkeli.comm.util.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SLDUtils {
	public static List reverse(List list){
		List l = new ArrayList();
		for (int i = list.size()-1; i >= 0; i--) {
			l.add(list.get(i));
		}
		return l;
	}
	
	public static void drawLine(Graphics2D graphics,Color color,List<PointF> pointList){
		graphics.setColor(color);
//		for(int i=0;i<pointList.size()-1;i++){
//			graphics.drawLine((int)pointList.get(i).getX(), (int)pointList.get(i).getY(), (int)pointList.get(i+1).getX(), (int)pointList.get(i+1).getY());
//		}
		graphics.fillPolygon(getPolygon(pointList));
	}
	
	public static Polygon getPolygon(List<PointF> pointList){
		int[] xpoints = new int[pointList.size()];
		int[] ypoints = new int[pointList.size()];
		for(int i=0;i<pointList.size();i++){
			xpoints[i] = (int)pointList.get(i).getX();
			ypoints[i] = (int)pointList.get(i).getY();
		}
		return new Polygon(xpoints,ypoints,pointList.size());
	}
	
	public static List<MapPoint> getMapPointList(String wkt){
		String regEx="LINESTRING \\((.*)\\)"; 
		Pattern p=Pattern.compile(regEx); 
		Matcher m=p.matcher(wkt); 
		boolean rs=m.find(); 
		String result = "";
		if(rs)
		{
			for(int i=1;i<=m.groupCount();i++){ 
				result+=m.group(i);
			} 
		} 
		//System.out.println(result);
		String[] pointArr = result.split(", ");
		MapPoint mp1 = new MapPoint(Double.parseDouble(pointArr[0].split(" ")[0].toString()),Double.parseDouble(pointArr[0].split(" ")[1].toString()));
		MapPoint mp2 = new MapPoint(Double.parseDouble(pointArr[1].split(" ")[0].toString()),Double.parseDouble(pointArr[1].split(" ")[1].toString()));		
		List<MapPoint> list = new ArrayList<MapPoint>();
		list.add(mp1); list.add(mp2);
		return list;
	}
}
