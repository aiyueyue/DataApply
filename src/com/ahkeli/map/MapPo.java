package com.ahkeli.map;

public class MapPo implements java.io.Serializable {
	public String mapType;//地图类型
	public int x;//单点坐标
	public int y;//单点坐标
	public int zoom;//级别
	
	public String type;//路径规划类型
	public String imgPath;//瓦片路径
	public double x0;//起始点
	public double y0;//起始点
	public double x1;//终止点
	
	public double y1;//终止点
	public String xy;//以逗号分隔的经纬度
	
	public String layerType;//图层类型（点："1"线："2"面："3"）
	public String layerName;//图层名称
	public String colsName;//返回的列名
	public String attributeCondition;//属性查询条件
	public double radius;//半径
	
	public String sectionno;//道路路段ID
	public String serverType;
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public double getX0() {
		return x0;
	}
	public void setX0(double x0) {
		this.x0 = x0;
	}
	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getY0() {
		return y0;
	}
	public void setY0(double y0) {
		this.y0 = y0;
	}
	public double getY1() {
		return y1;
	}
	public void setY1(double y1) {
		this.y1 = y1;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getXy() {
		return xy;
	}
	public void setXy(String xy) {
		this.xy = xy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZoom() {
		return zoom;
	}
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public String getColsName() {
		return colsName;
	}
	public void setColsName(String colsName) {
		this.colsName = colsName;
	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public String getAttributeCondition() {
		return attributeCondition;
	}
	public void setAttributeCondition(String attributeCondition) {
		this.attributeCondition = attributeCondition;
	}
	public String getSectionno() {
		return this.sectionno;
	}

	public void setSectionno(String sectionno) {
		this.sectionno = sectionno;
	}
	public String getLayerType() {
		return layerType;
	}
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}
