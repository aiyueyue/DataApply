package com.ahkeli.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/*import com.keli.action.TopBaseAction;
import com.keli.comm.util.com.map.FileOperate;
import com.keli.comm.util.com.map.StaticVar;
import com.keli.comm.util.com.map.StringFormat;
import com.keli.po.map.com.map.MapPo;*/
import com.opensymphony.xwork2.ActionContext;

public class MapAction {

	public String GChinaVersionMap = "m@149";
    public String GChinaVersionSatellite = "s@83";
    public String GChinaVersionLabels = "h@119";
    public String GChinaVersionTerrain = "t@108,r@119";
    private final String sSecureGoogleWords = "Galileo";
	
	private MapPo mapPo;
	
	public void getMapImg(){		
		try {
		InputStream is=null;
		File file=new File(StaticVar.MAP_IMG_URL+mapPo.getImgPath());
		//System.out.println("------------"+com.map.StaticVar.MAP_IMG_URL+mapPo.getImgPath());
		//文件不存在使用空白图片代替
		if(!file.exists()){
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String templetDire = request.getSession().getServletContext().getRealPath("/img/map/blank.png");
			file=new File(templetDire);
		}
		if((file.length()==0)&&(mapPo.getMapType().equals("googleUtil") || mapPo.getMapType().equals("satelliteUtil") || mapPo.getMapType().equals("Labels"))){
			String urlStr=this.getImageUrl();
			URL url = new URL(urlStr); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			is=connection.getInputStream();
			FileOperate.newFolder(StaticVar.MAP_IMG_URL+mapPo.getImgPath());
			FileOperate.upFile(is, StaticVar.MAP_IMG_URL + mapPo.getImgPath());
		}
		is=new FileInputStream(file);
		
		ActionContext ctx = ActionContext.getContext();       
		HttpServletResponse response=(HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType("image/png");
        OutputStream os = response.getOutputStream(); 
        byte[] bs = new byte[1024];    
        int len=0;        
        while((len=is.read(bs))!=-1)
        	os.write(bs,0,len);       
        is.close();
        os.close();
		}catch (Exception e) {
            e.printStackTrace();
		}
	}
	
	private String getImageUrl(){
        if (mapPo.x < 0 || mapPo.y < 0) return "";
        int iServerNumber = (mapPo.x + 2 * mapPo.y) % 4;
        String sXLngAppend = "";
        String sSecureGoogleWordsPartial = sSecureGoogleWords.substring(0, ((mapPo.x * 3) + mapPo.y) % 8);
        if (mapPo.y >= 10000 && mapPo.y < 100000){
            sXLngAppend = "&s=";
        }
        if(mapPo.getMapType().equals("googleUtil"))      
            return StringFormat.format("http://mt{0}.google.cn/vt/lyrs={1}&hl=zh-CN&gl=cn&x={2}{3}&y={4}&z={5}&s={6}", iServerNumber, GChinaVersionMap, mapPo.x, sXLngAppend, mapPo.y,mapPo.zoom, sSecureGoogleWordsPartial);                 
        else if(mapPo.getMapType().equals("satelliteUtil"))        
       	 	return StringFormat.format("http://mt{0}.google.cn/vt/lyrs={1}&gl=cn&x={2}{3}&y={4}&z={5}&s={6}", iServerNumber, GChinaVersionSatellite, mapPo.x, sXLngAppend, mapPo.y,mapPo.zoom, sSecureGoogleWordsPartial);
        else if(mapPo.getMapType().equals("Labels"))        
            return StringFormat.format("http://mt{0}.google.cn/vt/imgtp=png32&lyrs={1}&hl=zh-CN&gl=cn&x={2}{3}&y={4}&z={5}&s={6}", iServerNumber, GChinaVersionLabels, mapPo.x, sXLngAppend, mapPo.y, mapPo.zoom, sSecureGoogleWordsPartial);
        else if(mapPo.getMapType().equals("satelliteUtil"))        
       	 return StringFormat.format("http://mt{0}.google.cn/vt/lyrs={1}&hl=zh-CN&gl=cn&x={2}{3}&y={4}&z={5}&s={6}", iServerNumber, GChinaVersionTerrain, mapPo.x, sXLngAppend, mapPo.y, mapPo.zoom, sSecureGoogleWordsPartial);
       else
       	 return "";
    }
	
	//谷歌版本
    private void TryCorrectGoogleVersions(){
    	try{
    		URL url=new URL("http://ditu.google.com");
    		InputStream is=url.openStream();
    		BufferedReader in = new BufferedReader(new InputStreamReader(is)); 
    		StringBuffer buffer = new StringBuffer(); 
    	    String line = ""; 
    	    while ((line = in.readLine()) != null){ 
    	        buffer.append(line); 
    	      } 
    	    System.out.println(buffer.toString());
            }
            catch (Exception ex){};
        }

	public MapPo getMapPo() {
		return mapPo;
	}

	public void setMapPo(MapPo mapPo) {
		this.mapPo = mapPo;
	}

	public void outputInfo()
	{
		System.out.println("hello world");
	}
}
