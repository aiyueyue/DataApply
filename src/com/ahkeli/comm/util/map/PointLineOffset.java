package com.ahkeli.comm.util.map;

import java.util.ArrayList;
import java.util.List;

public class PointLineOffset {
	/**
	 * 要进行偏移的点串
	 */
//	public static List points = new ArrayList();
	
	/**
	 * 点串（线）的偏移
	 * @param iPixelOffset 偏移距离
	 * @return 返回偏移后的点串
	 */
	public static List getOneSideBuffer(int iPixelOffset, List points){
		List pointList = new ArrayList();
		
		//如果点串points中只有一个节点，则返回该点的缓冲区
		if(points.size() == 1){
			pointList.add(new PointF(((PointF)points.get(0)).getX(), ((PointF)points.get(0)).getY()));
			return pointList;
		}
		
		int count = points.size();
		
		//去除点串points中存在的三点共线的相邻三点中的中间点
		if (count > 2)
	    {
	        for (int i = count - 3 ; i >= 0; i--)
	        {
	        	//新添加注释：中间的点与两边的其中一个点存在重合的情况
	            if (((PointF)points.get(i)).getX()==((PointF)points.get(i+1)).getX() && ((PointF)points.get(i)).getY()==((PointF)points.get(i+1)).getY() || ((PointF)points.get(i+1)).getX()==((PointF)points.get(i+2)).getX() && ((PointF)points.get(i+1)).getY()==((PointF)points.get(i+2)).getY())
	            {
	                points.remove(i+1);
	                continue;                       
	            }
	
	            //斜率不存在的情况       
	            if (((PointF)points.get(i)).getX() == ((PointF)points.get(i+1)).getX() && ((PointF)points.get(i+1)).getX() == ((PointF)points.get(i+2)).getX())
	            {
	                points.remove(i+1);
	                continue;                        
	            }
	
	            //斜率存在且相等的情况
	            if (((PointF)points.get(i)).getX() != ((PointF)points.get(i+1)).getX() && ((PointF)points.get(i+1)).getX() != ((PointF)points.get(i+2)).getX())
	            {
	                float K1 = (((PointF)points.get(i)).getY() - ((PointF)points.get(i+1)).getY()) / (((PointF)points.get(i)).getX() - ((PointF)points.get(i+1)).getX());
	                float K2 = (((PointF)points.get(i+1)).getY() - ((PointF)points.get(i+2)).getY()) / (((PointF)points.get(i+1)).getX() - ((PointF)points.get(i+2)).getX());
	
	                if (K1 == K2)
	                {
	                    points.remove(i+1); 
	                    continue;                            
	                }
	            }
	        }
	    }

	    //下面是第一个节点的偏移
	    if (((PointF)points.get(0)).getX() == ((PointF)points.get(1)).getX())
	    {
	        if (((PointF)points.get(0)).getY() < ((PointF)points.get(1)).getY())
	            pointList.add(new PointF(((PointF)points.get(0)).getX() + iPixelOffset, ((PointF)points.get(0)).getY()));
	        else
	            pointList.add(new PointF(((PointF)points.get(0)).getX() - iPixelOffset, ((PointF)points.get(0)).getY()));
	    }
	    else
	    {
	    	double K = (((PointF)points.get(1)).getY() - ((PointF)points.get(0)).getY()) / (((PointF)points.get(1)).getX() - ((PointF)points.get(0)).getX());
	        double angle = Math.atan(Math.abs(K));
	         
	        double deltX = iPixelOffset * Math.sin(angle);
	        double deltY = iPixelOffset * Math.cos(angle);
	
	        double vectorX = ((PointF)points.get(1)).getX() - ((PointF)points.get(0)).getX();
	
	        if (K > 0)
	        {
	            if (vectorX > 0)
	                deltX = -deltX;
	            else
	                deltY = -deltY;
	        }
	        else
	        {
	            if (vectorX < 0)
	            {
	                deltX = -deltX;
	                deltY = -deltY;
	            }
	        }
	
	        pointList.add(new PointF((float)(((PointF)points.get(0)).getX() - deltX), (float)(((PointF)points.get(0)).getY() - deltY)));
	    }

	    //如果点串points节点数多于2个，则进行除第一个点和最后一个点以外的节点，即中间点的偏移
	    if (points.size() > 2)
	    {
	        for (int i = 0; i < points.size() - 2; i++)
	        {
	            PointF p = getMiddlePointBufferCorrdinate((PointF)points.get(i), (PointF)points.get(i+1), (PointF)points.get(i+2), iPixelOffset);
	            pointList.add(p);
	        }
	    }

	    //下面是最后一个节点的偏移
	    int num = points.size();
	
	    if (((PointF)points.get(num-2)).getX() == ((PointF)points.get(num-1)).getX())
	    {
	        if (((PointF)points.get(num-2)).getY() < ((PointF)points.get(num-1)).getY())                   
	            pointList.add(new PointF(((PointF)points.get(num-1)).getX() + iPixelOffset,((PointF)points.get(num-1)).getY()));
	        else
	            pointList.add(new PointF(((PointF)points.get(num-1)).getX() - iPixelOffset,((PointF)points.get(num-1)).getY()));
	    }
	    else
	    {
	        double K = (((PointF)points.get(num-2)).getY() - ((PointF)points.get(num-1)).getY()) / (((PointF)points.get(num-2)).getX() - ((PointF)points.get(num-1)).getX());
	        double angle = Math.atan(Math.abs(K));
	
	        double deltX = iPixelOffset * Math.sin(angle);
	        double deltY = iPixelOffset * Math.cos(angle);
	
	        double vectorX = ((PointF)points.get(num-1)).getX() - ((PointF)points.get(num-2)).getX();
	
	        if (K > 0)
	        {
	            if (vectorX > 0)
	                deltX = -deltX;
	            else
	                deltY = -deltY;
	        }
	        else
	        {
	            if (vectorX < 0)
	            {
	                deltX = -deltX;
	                deltY = -deltY;
	            }
	        }
	
	        pointList.add(new PointF((float)(((PointF)points.get(num-1)).getX() - deltX), (float)(((PointF)points.get(num-1)).getY() - deltY)));
	    }
	    return pointList;
	}

	/**
	 * 中心函数
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param distance 距离
	 * @return
	 */
	public static PointF getMiddlePointBufferCorrdinate(PointF p1, PointF p2, PointF p3, int distance){
        PointF returnPoint = new PointF() ;

	    //下面所做的处理是：在多段线的相邻两断线段的斜率只有一个存在的情况                   
	    float K = Float.MAX_VALUE;
	
	    if (p1.getX() == p2.getX())
	        K = (p2.getY() - p3.getY()) / (p2.getX() - p3.getX());
	
	    if (p2.getX() == p3.getX())
	        K = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
	
	    if (K != Float.MAX_VALUE)
	    {
	        double angle1 = Math.atan(Math.abs(K));
	        double angle2 = 0;
	        double deltaX = 0;
	        double deltaY = 0;
	
	        double vectorX1 = p2.getX() - p1.getX();
	        double vectorY1 = p2.getY() - p1.getY();
	        double vectorX2 = p3.getX() - p2.getX();
	        double vectorY2 = p3.getY() - p2.getY();
	
	        if (((vectorX1 * vectorY2 < 0 || vectorX2 * vectorY1 < 0) && K >= 0) ||
	            ((vectorX1 * vectorY2 > 0 || vectorX2 * vectorY1 > 0) && K <= 0))
	        {
	            angle2 = 0.25 * Math.PI + 0.5 * angle1;
	            deltaX = distance;
	            deltaY = (distance * Math.tan(angle2));
	        }
	        if (((vectorX1 * vectorY2 > 0 || vectorX2 * vectorY1 > 0) && K > 0) ||
	            ((vectorX1 * vectorY2 < 0 || vectorX2 * vectorY1 < 0) && K < 0))
	        {
	            angle2 = 0.25 * Math.PI - 0.5 * angle1;
	            deltaX = distance;
	            deltaY = (distance * Math.tan(angle2));
	        }
	
	        if (vectorY1 < 0 && vectorX2 < 0 ||
	            vectorX1 < 0 && vectorY2 < 0)
	            deltaY = -deltaY;
	        if (vectorY1 > 0 && vectorX2 > 0 ||
	            vectorX1 > 0 && vectorY2 > 0)
	            deltaX = -deltaX;
	        if (vectorX1 < 0 && vectorY2 > 0 && vectorX2 == 0)
	        {
	            deltaX = -deltaX;
	            deltaY = -deltaY;
	        }
	        if (vectorY1 > 0 && vectorX2 < 0 && vectorX1 == 0)
	        {
	            deltaX = -deltaX;
	            deltaY = -deltaY;
	        }
	
	        returnPoint = new PointF((float)(p2.getX() - deltaX), (float)(p2.getY() - deltaY));
	    }


	    //下面处理的是：在多段线的相邻两断线段斜率都存在的情况
	    if (p1.getX() != p2.getX() && p2.getX() != p3.getX())
	    {
	    	double vectorX1 = p2.getX() - p1.getX();
		    double vectorX2 = p3.getX() - p2.getX();
		    double K1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
		    double K2 = (p2.getY() - p3.getY()) / (p2.getX() - p3.getX());
		
		    double a1 = Math.atan(Math.abs(K1));
		    double a2 = Math.atan(Math.abs(K2));

	        //下面处理的是：在多段线的相邻两断线段斜率都存在且同号的情况 
	        if (K1 * K2 >= 0)
	        {
	        	double deltaX0 = 0;
	        	double deltaY0 = 0;
	
	            if (vectorX1 * vectorX2 > 0)
	            {
	            	double a3 = 0.5 * Math.abs(Math.PI - a1 - a2);
	
	            	double a4 = a3 + ((a1 > a2) ? a2 : a1);
	            	double s = distance / Math.sin(a4);
	
	                deltaX0 = (s * Math.cos(a3));
	                deltaY0 = (s * Math.sin(a3));
	            }
	            else
	            {
	            	double a3 = 0.5 * Math.abs(a1 - a2);
	
	            	double a4 = a3 + ((a1 > a2) ? a2 : a1);
	            	double s = distance / Math.sin(a3);
	
	                deltaX0 = (s * Math.cos(a4));
	                deltaY0 = (s * Math.sin(a4));
	            }
	
	            if (K1 >= 0 && K2 >= 0)
	            {
	                if (vectorX1 < 0 && vectorX2 < 0)
	                    deltaY0 = -deltaY0;
	
	                if (vectorX1 > 0 && vectorX2 > 0)
	                    deltaX0 = -deltaX0;
	
	                if (vectorX1 > 0 && vectorX2 < 0 && a1 > a2)
	                {
	                    deltaX0 = -deltaX0;
	                    deltaY0 = -deltaY0;
	                }
	
	                if (vectorX1 < 0 && vectorX2 > 0 && a1 < a2)
	                {
	                    deltaX0 = -deltaX0;
	                    deltaY0 = -deltaY0;
	                }
	
	            }
	
	            if (K1 <= 0 && K2 <= 0)
	            {
	
	                if (vectorX1 < 0 && vectorX2 < 0)
	                {
	                    deltaX0 = -deltaX0;
	                    deltaY0 = -deltaY0;
	                }
	
	                if (vectorX1 > 0 && vectorX2 < 0)
	                {
	                    if (a1 > a2)
	                        deltaY0 = -deltaY0;
	                    else
	                        deltaX0 = -deltaX0;
	                }
	
	                if (vectorX1 < 0 && vectorX2 > 0)
	                {
	                    if (a1 < a2)
	                        deltaY0 = -deltaY0;
	                    else
	                        deltaX0 = -deltaX0;
	
	                }
	
	            }
	
	            returnPoint = new PointF((float)(p2.getX() - deltaX0), (float)(p2.getY() - deltaY0));
	
	        }

	        //下面处理的是：在多段线的相邻两断线段斜率都存在且异号的情况
	        if (K1 * K2 < 0)
	        {
	        	double deltaX1 = 0;
	        	double deltaY1 = 0;
	
	            if (vectorX1 * vectorX2 > 0)
	            {
	                double a3 = 0.5 * (Math.PI - a1 - a2);
	
	                double s = distance / Math.sin(a3);
	                double a4 = a3 + ((a1 > a2) ? a2 : a1);
	
	                deltaX1 = (s * Math.cos(a4));
	                deltaY1 = (s * Math.sin(a4));
	            }
	            else
	                if (vectorX1 * vectorX2 < 0)
	                {
	                	double a3 = 0.5 * (a1 + a2);
	
	                	double s = distance / Math.sin(a3);
	                	double a4 = 0.5 * Math.abs(a1 - a2);
	
	                    deltaX1 =  (s * Math.cos(a4));
	                    deltaY1 =  (s * Math.sin(a4));
	                }
	
	            if (K1 < 0 && K2 > 0)
	            {
	
	                if (vectorX1 > 0 && vectorX2 > 0 && a1 < a2)
	                    deltaX1 = -deltaX1;
	
	                if (vectorX1 < 0 && vectorX2 < 0)
	                {
	                    deltaY1 = -deltaY1;
	                    if (a1 > a2)
	                        deltaX1 = -deltaX1;
	                }
	
	
	                if (vectorX1 > 0 && vectorX2 < 0 && a1 > a2)
	                    deltaY1 = -deltaY1;
	                if (vectorX1 < 0 && vectorX2 > 0)
	                {
	                    deltaX1 = -deltaX1;
	                    if (a1 < a2)
	                        deltaY1 = -deltaY1;
	                }
	
	            }
	
	            if (K1 > 0 && K2 < 0)
	            {
	
	                if (vectorX1 > 0 && vectorX2 > 0 && a1 > a2)
	                    deltaX1 = -deltaX1;
	
	                if (vectorX1 < 0 && vectorX2 < 0)
	                {
	                    deltaY1 = -deltaY1;
	                    if (a1 < a2)
	                        deltaX1 = -deltaX1;
	                }
	
	                if (vectorX1 > 0 && vectorX2 < 0)
	                {
	                    deltaX1 = -deltaX1;
	                    if (a1 > a2)
	                        deltaY1 = -deltaY1;
	                }
	                if (vectorX1 < 0 && vectorX2 > 0 && a1 < a2)
	                    deltaY1 = -deltaY1;
	            }
	
	            returnPoint = new PointF((float)(p2.getX() - deltaX1), (float)(p2.getY() - deltaY1));
	        }
	    }
	    return returnPoint;
	}
	
	public static void main(String[] a)
	{
    	double K = (43f-31f) / (87f-117f);
        double angle = Math.atan(Math.abs(0.666));
         
        double deltX = 3 * Math.sin(angle);
        double deltY = 3 * Math.cos(angle);
        
        
	}
	
}
