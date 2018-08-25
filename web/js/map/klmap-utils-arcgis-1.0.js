//klmap工具说明-----------------------
//x,y 代表经纬度
//pixelX,pixelY 代表像素坐标
//地图div的id和class为map
// 2013.02.26 初始版本完成 吕建成 
//------------------------------------
//2013.03.21 解决ToolTips闪动问题 吕
//2013.04.02 增加画虚线功能 吕
//2013.04.17 添加marker-with-title 房成

//titleContent显示
///添加一个点
//pointLayer 点图层
//x 经度坐标
//y 纬度坐标
//id 点id
//name 点name
//popupFeature popup特征对象
//clickCallback 点击点的回调函数
//iconWidth 点图标宽度
//iconHeight 点图标高度
//iconPicUrl 点图标图片URL
//isOffset 是否偏移
//isHand 是否显示手型
//isShowToolTips 是否显示ToolTips
//toolTipsWidth ToolTips宽度（isShowToolTips = true 时有效）
//toolTipsHeight ToolTips高度（isShowToolTips = true 时有效）
//toolTipsHtmlContent ToolTips内容（isShowToolTips = true 时有效）
///return 返回这个点对象
function addPoint(pointLayer, x, y, id, name, popupFeature, clickCallback, iconWidth, iconHeight, iconPicUrl, iconOffsetX, iconOffsetY, isOffset, isHand,
		  isShowToolTips, toolTipsWidth, toolTipsHeight, toolTipsHtmlContent,titleContent){
	var lonlat;
	if(isOffset==null || isOffset){
		if(serverType == "arcgisUtil"){
			lonlat = new OpenLayers.LonLat(parseFloat(x) ,  parseFloat(y) ).transform(
					 map.displayProjection, map.getProjectionObject()
				      );
		}else{
			
			lonlat = new OpenLayers.LonLat(parseFloat(x) + offsetX,  parseFloat(y) + offsetY).transform(
					 map.displayProjection, map.getProjectionObject()
				      );
		}
		
	}
	else{
		lonlat = new OpenLayers.LonLat(parseFloat(x),  parseFloat(y)).transform(
		         map.displayProjection, map.getProjectionObject()
		      );
	}
	if(iconWidth == null) iconWidth = 24;
	if(iconHeight == null) iconHeight = 22;
	if(iconPicUrl == null) iconPicUrl = 'point.png';
	var size = new OpenLayers.Size(iconWidth, iconHeight);
	var offset;
	if(iconOffsetX!=null && iconOffsetY!=null)
		offset = new OpenLayers.Pixel(iconOffsetX, iconOffsetY);
	else
		offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
	var icon = new OpenLayers.Icon(iconPicUrl, size, offset);
	var marker = new OpenLayers.Marker(lonlat,icon);
	if(titleContent!=""&&titleContent!=null){
	 marker.icon.imageDiv.title = titleContent;
	}
	marker.x = x;
	marker.y = y;
	marker.id = id;
	marker.name = name;
	var callback = function(evt){
			if (this.popup == null) {
			    this.popup = this.createPopup(this.closeBox);
			    map.addPopup(this.popup);
			    this.popup.show();
			} else {
			    this.popup.toggle();
			}
			currentPopup = this.popup;
			OpenLayers.Event.stop(evt);		
	}
	//使用参数popupFeature显示弹窗内容，使用内置回调函数切换
	if(popupFeature != null && clickCallback == null) 
		marker.events.register("click", popupFeature, callback);
	//只使用传入的回调函数
        if(popupFeature == null && clickCallback != null)
		marker.events.register("click", null, clickCallback);
	//使用参数popupFeature显示弹窗内容，且使用传入的回调函数
        if(popupFeature != null && clickCallback != null)
		marker.events.register("click", popupFeature, clickCallback);
	//是否显示鼠标移入手型
	if(isHand==null || isHand){
		marker.events.register("mouseover", null, function(){jQuery("#map").css("cursor","pointer");});        
		marker.events.register("mouseout", null, function(){jQuery("#map").css('cursor', '');}); 
	}
	//是否显示ToolTips
	if(isShowToolTips){
		var popup;
		if(toolTipsWidth == null) toolTipsWidth = 200;
		if(toolTipsHeight == null) toolTipsHeight = 200;
		if(toolTipsHtmlContent == null) toolTipsHtmlContent = name;
		marker.events.register("mouseover", null, function(){
			if(popup == null){
				popup = getPopupCloud(popup, "popup", x, y, toolTipsWidth, toolTipsHeight, toolTipsHtmlContent, null, false);
				map.addPopup(popup);
				popup.events.register("mouseover", null, function(){
					popup.show();
				});
			}
			popup.show();
		});
		marker.events.register("mouseout", null, function(){
			popup.hide();
			popup.events.register("mouseout", null, function(){
				popup.hide();
			});
		});
	}
	pointLayer.addMarker(marker);
	return marker;
}

///添加一个不偏移的点
function addPointNoOffset(pointLayer, x, y, id, name, popupFeature, clickCallback, iconWidth, iconHeight, iconPicUrl, iconOffsetX, iconOffsetY){
	addPoint(pointLayer, x, y, id, name, popupFeature, clickCallback, iconWidth, iconHeight, iconPicUrl, iconOffsetX, iconOffsetY, false);
}

///添加一条线
//lineLayer 线图层
//strPoint 点集字符串用逗号分割
//color 线颜色值
//opacity 线透明度
//width 线宽
//lineStyle 线样式 “dot”, “dash”, “dashdot”, “longdash”, “longdashdot”, or “solid”
function addLine(lineLayer, strPoint, color, opacity, width, lineStyle){
    var arrPoint = strPoint.split(",");
    var points = new Array();
    for ( var i=0,j=0; i < arrPoint.length-1; i=i+2,j++ ) {
    	if(serverType == "arcgisUtil"){
    		var x = parseFloat(arrPoint[i]);
    		var y = parseFloat(arrPoint[i+1]);
    	}else{
    		
    		var x = parseFloat(arrPoint[i]) + offsetX;
    		var y = parseFloat(arrPoint[i+1]) + offsetY;
    	}
	
	points[j] = new OpenLayers.Geometry.Point(x, y).transform(map.displayProjection, map.getProjectionObject());	
    }
    var line = new OpenLayers.Geometry.LineString(points); 
    var style = {
        strokeColor: color,
        strokeOpacity: opacity,
        strokeWidth: width,
	strokeDashstyle: lineStyle
    };
    var lineFeature = new OpenLayers.Feature.Vector(line, null, style);	
    lineLayer.addFeatures(lineFeature);
}

///获得一个popup对象
//popup popup对象
//name popup名称
//x 经度坐标
//y 纬度坐标
//width popup宽度
//height popup高度
//html popup内容（html字符串）
//closeBoxCallback 关闭窗口回调函数
//closeBox 是否显示关闭按钮boolean值
///return 返回一个popup对象
function getPopup(popup, name, x, y, width, height, html, closeBoxCallback, closeBox){
	var lonlat = new OpenLayers.LonLat(parseFloat(x) + offsetX, parseFloat(y) + offsetY).transform(
				 map.displayProjection, map.getProjectionObject());
	if(closeBox == null) closeBox = true;
	popup = new OpenLayers.Popup(name,lonlat,new OpenLayers.Size(width, height), html, closeBox, closeBoxCallback);
	popup.panMapIfOutOfView = true;
	return popup;
}

///获得一个popup对象(云团样式)
//popup popup对象
//name popup名称
//x 经度坐标
//y 纬度坐标
//width popup宽度
//height popup高度
//html popup内容（html字符串）
//closeBoxCallback 关闭窗口回调函数
//closeBox 是否显示关闭按钮boolean值
///return 返回一个popup对象
function getPopupCloud(popup, name, x, y, width, height, html, closeBoxCallback, closeBox){
	var lonlat = new OpenLayers.LonLat(parseFloat(x) + offsetX, parseFloat(y) + offsetY).transform(
				 map.displayProjection, map.getProjectionObject());	
	if(closeBox == null) closeBox = true;
	popup = new OpenLayers.Popup.FramedCloud(name,lonlat,new OpenLayers.Size(width, height), html, null, closeBox, closeBoxCallback);
	return popup;
}

///获得弹出窗口特征值
//layer 图层
//x 经度坐标
//y 纬度坐标
//popupClass popup样式
//popupContentHTML popup内容（支持html）
//closeBox 是否显示关闭按钮
//overflow 是否覆盖
function getPopupFeature(layer, x, y, popupClass, popupContentHTML, closeBox, overflow){
	var lonlat = new OpenLayers.LonLat(parseFloat(x) + offsetX, parseFloat(y) + offsetY).transform(
				 map.displayProjection, map.getProjectionObject());
	var feature = new OpenLayers.Feature(layer, lonlat);
	feature.closeBox = closeBox;
	feature.popupClass = popupClass;
	feature.data.popupContentHTML = popupContentHTML;
	feature.data.overflow = (overflow) ? "auto" : "hidden";
	return feature;
}

///从一个像素坐标获得一个经纬度(客户端使用)
//pixelX 屏幕坐标x
//pixelY 屏幕坐标y
///return 经纬度坐标
function getLonLatFromPixel(pixelX, pixelY){
	var lonLat = map.getLonLatFromPixel(new OpenLayers.Pixel(pixelX, pixelY)).transform(map.getProjectionObject(), map.displayProjection);
	return lonLat;
}

///获得经纬度(客户端使用)
//x 经纬度坐标x
//y 经纬度坐标y
///return 加过偏移量的经纬度
function getLonLat(x,y){
	var lonLat = new OpenLayers.LonLat(parseFloat(x) + offsetX, parseFloat(y) + offsetY);
	lonLat.transform(map.displayProjection, map.getProjectionObject());
	return lonLat;
}

///获得经纬度(服务端使用)
//x 经纬度坐标x
//y 经纬度坐标y
///return 减过偏移量的经纬度
function getLonLatForServer(x,y){
	var lonLat = new OpenLayers.LonLat(parseFloat(x) - offsetX, parseFloat(y) - offsetY);
	return lonLat;
}
