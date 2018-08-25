package com.ahkeli.map;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

//import com.keli.action.TopBaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JtllImageAction {
	private float centerX;
	private float centerY;
	private int zoom;
	private int width;
	private int height;
	private String forecastTime; //预测时间  five  ten  fifteen  thirty
	private String mapType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
	
	//测试URL
	//http://127.0.0.1:8080/Kljtxxfww_1_0_v/getJtllImage.action?centerX=117.28519&centerY=31.863563&zoom=13&width=800&height=700
	
	public void getJtllImage(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		long t1 = new Date().getTime();
		BufferedImage image = null;
		try {
			Map sslkMap=new HashMap();
			List<Map> lkList=new ArrayList();
			//声明类的对象
			//ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:src/applicationContext.xml");
			/*try {
				ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config/spring-dao/spring-mybatis.xml");
			}catch (Exception e)
			{ e.printStackTrace();}*/
            GetDrawLineData data = new GetDrawLineData();
            //SqlSession sqlSession = ((GetImpRoadImgData) ac.getBean("getDrawLineData")).getSqlSession();
            //data.setOwnSqlSession(sqlSession);
			if(forecastTime!=null && !forecastTime.equals("") && !forecastTime.equals("undefined"))//预测实时路况
			{
				//lkList= TopBaseAction.getFcdBean().getWlSslkList(forecastTime);
				System.out.println("无预测时间");
			}
			else //实时路况
			//sslkMap = TopBaseAction.getJtllBean().getSslkPo().getSslkMap();
			//List lkList = (List)sslkMap.get("lkList");
			{
				lkList = data.getSslkList();
                /*Map temp = new HashMap();
                temp.put("ID",232);
                temp.put("STATUS",5);
                temp.put("SPEED",8);
                temp.put("ROADNAME","城南路");
                temp.put("ROADTYPE",8);
                temp.put("XY","113.670139451,34.746970026,113.671312815,34.746792175,113.671682375,34.746729424,113.673098848,34.746488908,113.675294658,34.746171301,113.67599228,34.746055804");
                temp.put("COLOR","0xFF0000");
                lkList.add(temp);
                Map temp1 = new HashMap();
                temp1.put("ID",232);
                temp1.put("STATUS",5);
                temp1.put("SPEED",8);
                temp1.put("ROADNAME","城南路");
                temp1.put("ROADTYPE",8);
                temp1.put("XY","113.67599228,34.746055804,113.675294658,34.746171301,113.673098848,34.746488908,113.671682375,34.746729424,113.671312815,34.746792175,113.670139451,34.746970026");
                temp1.put("COLOR","0xFF0000");
                lkList.add(temp1);
                Map temp2 = new HashMap();
                temp2.put("ID",91);
                temp2.put("STATUS",3);
                temp2.put("SPEED",15);
                temp2.put("ROADNAME","工人路");
                temp2.put("ROADTYPE",6);
                temp2.put("XY","113.614696238,34.733770831,113.614770327,34.730040148");
                temp2.put("COLOR","0xFFFF00");
                lkList.add(temp2);
                Map temp3 = new HashMap();
                temp3.put("ID",91);
                temp3.put("STATUS",3);
                temp3.put("SPEED",15);
                temp3.put("ROADNAME","工人路");
                temp3.put("ROADTYPE",6);
                temp3.put("XY","113.614770327,34.730040148,113.614696238,34.733770831");
                temp3.put("COLOR","0xFFFF00");
                lkList.add(temp3);*/
			}
			image = data.getJtllImage(centerX, centerY, zoom, width, height, "googleUtil", lkList);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		long t2 = new Date().getTime();
//		System.out.println(t2-t1);
		try {
			if(image != null)
				ImageIO.write(image, "png", response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*    public void getStatusSslkImage(){
        Date beg = new Date();
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        BufferedImage image = null;
        try {
            Map sslkMap=new HashMap();
            List lkList=new ArrayList();

            if(forecastTime!=null && !forecastTime.equals("") && !forecastTime.equals("undefined"))//预测实时路况
            {
                lkList= TopBaseAction.getLkfwBean().getWlSslkList(forecastTime);
            }else //实时路况
            {
                lkList=TopBaseAction.getLkfwBean().getSslkList();
            }
            image = TopBaseAction.getFcdBean().getJtllImage(centerX, centerY, zoom, width, height,mapType,lkList);
        } catch (Exception e1) {
            //e1.printStackTrace();
        }
        try {
            if(image != null){
                ImageIO.write(image, "png", response.getOutputStream());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date end = new Date();
        long bet = end.getTime() - beg.getTime();
//		System.out.println("getStatusSslkImage:"+bet);
    }*/
	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getForecastTime() {
		return forecastTime;
	}

	public void setForecastTime(String forecastTime) {
		this.forecastTime = forecastTime;
	}
	
	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
}
