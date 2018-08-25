package com.ahkeli.map;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringFormat {
    /**
     * 格式化数字
     * @param str
     * @param formatStr
     * @return
     */
    public static String decimalFormatStr(final String str,final String formatStr) {
    	DecimalFormat	format= new	DecimalFormat(formatStr); 
    	return format.format(Double.valueOf(str)); 
    }
    //字符串合并方法，返回一个合并后的字符串   
    public static String format(String str,Object ... args)   
    {   
  
        //这里用于验证数据有效性   
        if(str==null||"".equals(str))   
            return "";   
        if(args.length==0)   
        {   
            return str;   
        }   
  
        /*  
         *如果用于生成SQL语句，这里用于在字符串前后加单引号  
        for(int i=0;i<args.length;i++)  
        {  
            String type="java.lang.String";  
            if(type.equals(args[i].getClass().getName()))  
                args[i]="'"+args[i]+"'";  
        }  
        */  
  
        String result=str;   
  
        //这里的作用是只匹配{}里面是数字的子字符串   
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{(\\d+)\\}");   
        java.util.regex.Matcher m = p.matcher(str);   
  
        while(m.find())   
        {   
            //获取{}里面的数字作为匹配组的下标取值   
            int index=Integer.parseInt(m.group(1));   
  
            //这里得考虑数组越界问题，{1000}也能取到值么？？   
            if(index<args.length)   
            {   
  
                //替换，以{}数字为下标，在参数数组中取值   
                result=result.replace(m.group(),args[index].toString());   
            }   
        }   
        return result;   
    }
	public static Date formatStrToDate(String dateStr){
		if(dateStr==null || dateStr.equals("")) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date formatStrToDate_2(String dateStr){
		if(dateStr==null || dateStr.equals("")) return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date formatStrToDate_1(String dataStr){
		
		if(dataStr==null||dataStr.equals(""))return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=null;
		try {
			date=sdf.parse(dataStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public static String formatDate(Date dateString){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String date = null;

			date= sdf.format(dateString) ;
		 
		   return date;
	}   
	public static String getDatePre(String dateStr){    	
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	Date dateCur=null;
		try {
			dateCur = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Date date_pre=new Date(dateCur.getTime()-24*60*60*1000);
    	String datePreStr = null;
    	datePreStr= sdf.format(date_pre) ;
        return datePreStr;
    }
	public static String formatDateToStr_1(Date dateString){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String date = null;
		date= sdf.format(dateString) ;
		return date;
	}
	public static String formatDateToStr_2(Date dateString){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = null;
		date= sdf.format(dateString) ;
		return date;
	}
    public static String formatStr(String dateStr){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date date=null;
    	try {
			date=sdf.parse(dateStr);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
    	dateStr= sdf.format(date);
    	dateStr=dateStr.replaceFirst("-","年").replaceFirst("-","月"); 
		return dateStr+"日";
	}
    public static String getTime()  {
        String s = null;
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            s = simpledateformat.format(date);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return s;
    }
    
    public static Date getBeforeDay2(Date date,int i){
    	DateFormat   format=new   SimpleDateFormat("yyyy-MM-dd"); 
		String ss=null;
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_MONTH,-i);
    	return calendar.getTime();
    }
	public static String formatDateToStr_3(Date dateString){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    String date = null;
		date= sdf.format(dateString) ;
		return date;
	}

}
