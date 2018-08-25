//全局变量
var map;
var city = "ningbo";
var serverType = "arcgisUtil"; //arcgisUtil googleUtil satelliteUtil
var mapTileUrl = "http://61.153.213.218:8184/";
var serviceUrl = "http://61.153.213.218:8184/"; ;
var zoomLevel = 9;
var initZoom=3 ;
var minZoom = 00;
var maxZoom = 09;
var offsetX = -0.179656+0.006055;//-0.17429;
var offsetY = 0.0-0.001177;//-0.00197;
//var initCenterX =121.54898;//117.274246;
////34.7604027507547
//var initCenterX =113.653517617188;
//var initCenterY =34.7604027507547;//31.868665;
//var initCenterY =29.873892;//31.868665;
var initCenterX =113.653517617188;
var initCenterY =34.7616361246625;//31.868665;

//arcgis配置
var arcgisTileUrl ='http://127.0.0.1:7777/arcgis';
//var arcgisTileUrl='D:/TileResources/ningbo/arcgis';
var mapResolutions=[
    5.9486524036121E-04,
    2.97432620180605E-04,
    1.5228550153247E-04,
    7.61427507662348E-05,
    3.80713753831174E-05,
    1.90356876915587E-05,
    9.51784384577936E-06,
    4.75892192288968E-06,
    2.37946096144484E-06,
    1.78459572108363E-06];
if(serverType == "arcgisUtil"){
	 initZoom = 03;
	 offsetX = 0;// -0.17973;
	 offsetY = 0;//0;
}else{
	 initZoom = 11;
	 offsetX = -0.17429;// -0.17973;
	 offsetY = -0.00197;//0;
}
//地图图层定义
var arcgisTileLayer,googleTileLayer,googleStateLayer,googleLabelsLayer;

//地图初始化
function initMap(){
	map = new OpenLayers.Map("map", {
		maxExtent : new OpenLayers.Bounds(-20057508.3427892,-20037508.3427892, 20037508.3427892, 20037508.3427892),
		maxResolution : 156543.0339,
		numZoomLevels : zoomLevel,
		units : 'm',
		projection : "EPSG:900913",
		//displayProjection : new OpenLayers.Projection("EPSG:4326"),
		allOverlays: true,
		transparent: true
	});
	//map.addControl( new OpenLayers.Control.PanZoomBar());
	//arcgisTileLayer = new OpenLayers.Layer.TMS("Arcgis Tile", mapTileUrl, {'type' : 'png', 'getURL': getArcgisTileUrl});
	arcgisTileLayer = new OpenLayers.Layer.ArcGISCache('arcgis', arcgisTileUrl, {
				tileSize:new OpenLayers.Size(512,512),
				tileOrigin: new OpenLayers.LonLat(-400,400),
				resolutions: mapResolutions,
				sphericalMercator: true,
				useAGS: false,
				useArcGISServer: false,
				isBaseLayer: true,
				type: 'png'
			});
	googleTileLayer = new OpenLayers.Layer.TMS("Google Tile", mapTileUrl, {'type' : 'png',  'getURL': getGoogleTileUrl});
	googleStateLayer = new OpenLayers.Layer.TMS("Google State", mapTileUrl, {'type' : 'png', 'getURL' : getGoogleStateUrl});
	googleLabelsLayer = new OpenLayers.Layer.TMS("Google State", mapTileUrl, {'type' : 'png', 'getURL' : getGoogleLabelsUrl});
	//切换地图
	selectType(serverType);
	/*var lonLat = new OpenLayers.LonLat(initCenterX , initCenterY);
	lonLat.transform(map.displayProjection, map.getProjectionObject());
	map.setCenter(lonLat, 3);*/
	var lonLat = new OpenLayers.LonLat(initCenterX + offsetX, initCenterY + offsetY);
	lonLat.transform(map.displayProjection, map.getProjectionObject());
	map.setCenter(lonLat, initZoom);
	//控制缩放级别
	map.events.register('zoomend', this, function (event) {
       		var zoom = map.getZoom();
       		if(zoom < minZoom){	
       			map.zoomTo(minZoom);
       			//map.setCenter(lonLat, minZoom); //暂时不使用
       		}
       		if(zoom > maxZoom){
       			//map.zoomTo(maxZoom); //暂时不使用
       		 	//map.setCenter(lonLat, maxZoom); //暂时不使用
       		}
       	});
}

//地图切换
function selectType(type){
	serverType = type;
	if(serverType == "arcgisUtil"){
		if(map.getLayer(googleTileLayer) != null) map.removeLayer(googleTileLayer);
		if(map.getLayer(googleStateLayer) != null) map.removeLayer(googleStateLayer);
		if(map.getLayer(googleLabelsLayer) != null) map.removeLayer(googleLabelsLayer);
		map.addLayer(arcgisTileLayer);
	}else if(serverType == "googleUtil"){
		if(map.getLayer(arcgisTileLayer) != null) map.removeLayer(arcgisTileLayer);
		if(map.getLayer(googleStateLayer) != null) map.removeLayer(googleStateLayer);
		if(map.getLayer(googleLabelsLayer) != null) map.removeLayer(googleLabelsLayer);
		map.displayProjection = new OpenLayers.Projection("EPSG:4326");
		map.addLayer(googleTileLayer);
	}else if(serverType == "satelliteUtil"){
		if(map.getLayer(arcgisTileLayer) != null) map.removeLayer(arcgisTileLayer);
		if(map.getLayer(googleTileLayer) != null) map.removeLayer(googleTileLayer);
		map.displayProjection = new OpenLayers.Projection("EPSG:4326");
		map.addLayers([googleStateLayer,googleLabelsLayer]);
	}
}

//arcgis切片地图
function getArcgisTileUrl(bounds){
	var directory = 'arcgis';
	var res = this.map.getResolution();
	var x = Math.round((bounds.left - this.maxExtent.left)/(res * this.tileSize.w));
	var y = Math.round((this.maxExtent.top - bounds.top)/(res * this.tileSize.h));
	var z = this.map.getZoom();
	if(parseInt(z)<10) z='0'+z;
	var path= '/'+directory+'/'+'L'+z+'/'+x+'/'+y+'.'+this.type;
	var url = this.url+"getMapImg.action?mapPo.serverType="+serverType+"&mapPo.x="+x+"&mapPo.y="+y+"&mapPo.zoom="+z+"&mapPo.imgPath=";
    	if (url instanceof Array) url = this.selectUrl(path, url);
	return url + path;
}

//google切片地图
function getGoogleTileUrl(bounds){
	var directory = 'google/Map';
	var res = this.map.getResolution();
	var x = Math.round((bounds.left - this.maxExtent.left)/(res * this.tileSize.w));
	var y = Math.round((this.maxExtent.top - bounds.top)/(res * this.tileSize.h));
	var z = this.map.getZoom();
	if(parseInt(z)<10) z='0'+z;
	var path= '/'+directory+'/'+'L'+z+'/'+x+'/'+y+'.'+this.type;
	var url = this.url+"getMapImg.action?mapPo.serverType="+serverType+"&mapPo.x="+x+"&mapPo.y="+y+"&mapPo.zoom="+z+"&mapPo.imgPath=";
    	if (url instanceof Array) url = this.selectUrl(path, url);
	return url + path;	
}

//google卫星地图
function getGoogleStateUrl(bounds){
	var directory = 'google/state';
	var res = this.map.getResolution();
	var x = Math.round((bounds.left - this.maxExtent.left)/(res * this.tileSize.w));
	var y = Math.round((this.maxExtent.top - bounds.top)/(res * this.tileSize.h));
	var z = this.map.getZoom();
	if(parseInt(z)<10) z='0'+z;
	var path= '/'+directory+'/'+'L'+z+'/'+x+'/'+y+'.'+this.type;
	var url = this.url+"getMapImg.action?mapPo.serverType="+serverType+"&mapPo.x="+x+"&mapPo.y="+y+"&mapPo.zoom="+z+"&mapPo.imgPath=";
    	if (url instanceof Array) url = this.selectUrl(path, url);
	return url + path;
}

//google道路地图
function getGoogleLabelsUrl(bounds){
	var directory = 'google/Labels';
	var res = this.map.getResolution();
	var x = Math.round((bounds.left - this.maxExtent.left)/(res * this.tileSize.w));
	var y = Math.round((this.maxExtent.top - bounds.top)/(res * this.tileSize.h));
	var z = this.map.getZoom();
	if(parseInt(z)<10) z='0'+z;
	var path= '/'+directory+'/'+'L'+z+'/'+x+'/'+y+'.'+this.type;
	var url = this.url+"getMapImg.action?mapPo.serverType="+serverType+"&mapPo.x="+x+"&mapPo.y="+y+"&mapPo.zoom="+z+"&mapPo.imgPath=";
    	if (url instanceof Array) url = this.selectUrl(path, url);
	return url + path;	
}

//添加实时路况
//forecastTime 预测时间  five  ten  fifteen  thirty
function addSslkImg(sslkLayer, centerX, centerY, zoom, width, height, forecastTime){
	var pixel = new OpenLayers.Pixel(width, height);
	var lonlat = map.getLonLatFromPixel(pixel);
	var size = new OpenLayers.Size(width, height);
	var offset = new OpenLayers.Pixel(-size.w, -size.h);
	centerX-=offsetX;
	centerY-=offsetY;
	var imgUrl = serviceUrl+'getJtllImage.action?centerX='+centerX+'&centerY='+centerY+'&zoom='+zoom+'&width='+width+'&height='+height+'&forecastTime='+forecastTime;
	var icon = new OpenLayers.Icon(imgUrl, size, offset);
	var sslkMarker = new OpenLayers.Marker(lonlat,icon);
	sslkLayer.addMarker(sslkMarker);
}
//arcgis偏移量转换
function getOffSetXY(){
	
}
