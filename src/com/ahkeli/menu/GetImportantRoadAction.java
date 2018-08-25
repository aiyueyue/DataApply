package com.ahkeli.menu;

import com.ahkeli.map.GetDrawLineData;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pangbo on 2015/12/11.
 */
public class GetImportantRoadAction extends ActionSupport {
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

    public void getImgRoadImage(){
        ActionContext ctx = ActionContext.getContext();
        HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
        HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
//		long t1 = new Date().getTime();
        BufferedImage image = null;
        try {
            Map sslkMap=new HashMap();
            List<Map> lkList=new ArrayList();
            GetImpRoadImgData data = new GetImpRoadImgData();

            if(forecastTime!=null && !forecastTime.equals("") && !forecastTime.equals("undefined"))//预测实时路况
            {
                //lkList= TopBaseAction.getFcdBean().getWlSslkList(forecastTime);
                System.out.println("无预测时间");
            }
            else //实时路况
            //sslkMap = TopBaseAction.getJtllBean().getSslkPo().getSslkMap();
            //List lkList = (List)sslkMap.get("lkList");
            {
                lkList = data.getImpRoadSslkList();
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

