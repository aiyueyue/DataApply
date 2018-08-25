package com.ahkeli.comm.util.map;

import java.awt.Point;

public class WebMercator
{
    private double left = -177;
    private double bottom = -85.05112878;
    private double right = 177;
    private double top = 85.05112878;
    
    private Size tileSize = new Size(256, 256); //��Ƭ��С
    
    private double axis = 6378137; //�����峤����
    private double flattening = 1.0 / 298.257223563; //����
    
    /// <summary>
    /// ʹ���������ұ߽�������ī����ͶӰ���Զ�Ϊ��λ��
    /// </summary>
    /// <param name="left">��߽�</param>
    /// <param name="bottom">�±߽�</param>
    /// <param name="right">�ұ߽�</param>
    /// <param name="top">�ϱ߽�</param>
    public WebMercator(double left, double bottom, double right, double top)
    {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }
    
    public WebMercator(){};

    /// <summary>
    /// ��ͼ����ת��Ϊ��������
    /// </summary>
    /// <param name="x">��ͼ����X</param>
    /// <param name="y">��ͼ����Y</param>
    /// <param name="zoom">��ͼ���ż���</param>
    /// <returns>������������</returns>
    public Point fromMapPointToPixelPoint(double x, double y, int zoom)
    {
        Point pixelPoint = new Point(0,0);
        
        x = clip(x, left, right);
        y = clip(y, bottom, top);

        double dx = (x + 180) / 360;
        double sinLatitude = Math.sin(y * Math.PI / 180);
        double dy = 0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI);

        Size s = getTileMatrixSizePixel(zoom);

        int mapSizeX = s.getWidth();
        int mapSizeY = s.getHeight();

        pixelPoint.x = (int)clip(dx * mapSizeX + 0.5, 0, mapSizeX - 1);
        pixelPoint.y = (int)clip(dy * mapSizeY + 0.5, 0, mapSizeY - 1);

        return pixelPoint;
    }

    /// <summary>
    /// ��������ת��Ϊ��ͼ����
    /// </summary>
    /// <param name="x">��������X</param>
    /// <param name="y">��������Y</param>
    /// <param name="zoom">��ͼ���ż���</param>
    /// <returns>���ص�ͼ����</returns>
    public MapPoint fromPixelPointToMapPoint(int x, int y, int zoom)
    {
        MapPoint mapPoint = new MapPoint();

        Size s = getTileMatrixSizePixel(zoom);
        double mapSizeX = s.getWidth();
        double mapSizeY = s.getHeight();

        double xx = (clip(x, 0, mapSizeX - 1) / mapSizeX) - 0.5;
        double yy = 0.5 - (clip(y, 0, mapSizeY - 1) / mapSizeY);

        mapPoint.setX(360 * xx);
        mapPoint.setY(90 - 360 * Math.atan(Math.exp(-yy * 2 * Math.PI)) / Math.PI);

        return mapPoint;
    }
    
    //��ȡ ��Ƭ�����С
    private Size getTileMatrixSizePixel(int zoom)
    {
    	int xy = (1 << zoom);
        Size sMin = new Size(0, 0);
        Size sMax = new Size(xy - 1, xy - 1);
        Size s = new Size(sMax.getWidth() - sMin.getWidth() + 1, sMax.getHeight() - sMin.getHeight() + 1);
        return new Size(s.getWidth() * tileSize.getWidth(), s.getHeight() * tileSize.getHeight());
    }
    
    private double clip(double d, double min, double max)
    {
        return Math.min(Math.max(d, min), max);
    }
    
	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public Size getTileSize() {
		return tileSize;
	}

	public double getAxis() {
		return axis;
	}

	public double getFlattening() {
		return flattening;
	}
}
