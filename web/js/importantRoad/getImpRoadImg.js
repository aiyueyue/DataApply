/**
 * Created by pangbo on 2015/12/11.
 */
var serviceUrl = "http://127.0.0.1:8080/";
//forecastTime ‘§≤‚ ±º‰  five  ten  fifteen  thirty
function getImpRoadImg(sslkLayer, centerX, centerY, zoom, width, height, forecastTime){
    var pixel = new OpenLayers.Pixel(width, height);
    var lonlat = map.getLonLatFromPixel(pixel);
    var size = new OpenLayers.Size(width, height);
    var offset = new OpenLayers.Pixel(-size.w, -size.h);
    centerX-=offsetX;
    centerY-=offsetY;
    var imgUrl = serviceUrl+'getImpRoadImg.action?centerX='+centerX+'&centerY='+centerY+'&zoom='+zoom+'&width='+width+'&height='+height+'&forecastTime='+forecastTime;
    var icon = new OpenLayers.Icon(imgUrl, size, offset);
    var sslkMarker = new OpenLayers.Marker(lonlat,icon);
    sslkLayer.addMarker(sslkMarker);
}