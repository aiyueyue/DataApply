package com.ahkeli.map;

import java.util.Locale;
import java.util.ResourceBundle;

public class StaticVar {
	 
	static ResourceBundle KL_Config= ResourceBundle.getBundle("properties-config/keli",Locale.getDefault());
	
	public static String CITY = KL_Config.getString("CITY");
	public static int ROADID_NUM = Integer.parseInt(KL_Config.getString("ROADID_NUM"));
    public static String XYZPKNETWORK=KL_Config.getString("XYZPKNETWORK");
	public static int AID_NUM = Integer.parseInt(KL_Config.getString("AID_NUM"));
	public static boolean GPS_IS_ENCRYPT = KL_Config.getString("GPS_IS_ENCRYPT").equals("true")?true:false;
    public static String KZCL=KL_Config.getString("KZCL");
	
	public static int FCD_CALCU_CYCLE = Integer.parseInt(KL_Config.getString("FCD_CALCU_CYCLE"));
	public static int BUS_RANGE_ANGLE = Integer.parseInt(KL_Config.getString("BUS_RANGE_ANGLE"));
	public static int BUS_RANGE_DISTANCE = Integer.parseInt(KL_Config.getString("BUS_RANGE_DISTANCE"));
	
	public static double GRID_DELTA_X = Double.parseDouble(KL_Config.getString("GRID_DELTA_X"));
	public static double GRID_DELTA_Y = Double.parseDouble(KL_Config.getString("GRID_DELTA_Y"));
	public static int GRID_MATCH_EXP = Integer.parseInt(KL_Config.getString("GRID_MATCH_EXP"));
	public static String MAP_IMG_URL=KL_Config.getString("MAP_IMG_URL");
	
	public static int AID_CALCU_CYCLE = Integer.parseInt(KL_Config.getString("AID_CALCU_CYCLE"));
	
	public static int READ_MINUTE_PRE = Integer.parseInt(KL_Config.getString("READ_MINUTE_PRE"));
	public static int DETECT_CALCU_CYCLE = Integer.parseInt(KL_Config.getString("DETECT_CALCU_CYCLE"));
	public static String DETECT_DENSITY_ZONE=KL_Config.getString("DETECT_DENSITY_ZONE");
	public static int DETECT_ZONE=Integer.parseInt(KL_Config.getString("DETECT_ZONE"));
	public static int DETECT_DETECTTYPE=Integer.parseInt(KL_Config.getString("DETECT_DETECTTYPE"));
	public static int DETECT_DATA_MODE=Integer.parseInt(KL_Config.getString("DETECT_DATA_MODE"));
	public static double DETECT_VEHICLE_LEN=Double.parseDouble(KL_Config.getString("DETECT_VEHICLE_LEN"));
    public static int TPI_CALCU_CYCLE = Integer.parseInt(KL_Config.getString("TPI_CALCU_CYCLE"));
	public static int TRAVEL_CALCU_CYCLE = Integer.parseInt(KL_Config.getString("TRAVEL_CALCU_CYCLE"));
	public static String TRAVEL_DENSITY_ZONE=KL_Config.getString("TRAVEL_DENSITY_ZONE");
	public static String TRAVEL_VOLUME_ZONE=KL_Config.getString("TRAVEL_VOLUME_ZONE");
	public static int TRAVEL_ZONE=Integer.parseInt(KL_Config.getString("TRAVEL_ZONE"));
	
	public static int TCI_CALCU_CYCLE=Integer.parseInt(KL_Config.getString("TCI_CALCU_CYCLE"));
	public static String TCI_CONGESTION_ZONE=KL_Config.getString("TCI_CONGESTION_ZONE");
	public static int TCI_ZONE=Integer.parseInt(KL_Config.getString("TCI_ZONE"));
	public static String TCI_ISV_VALUE=KL_Config.getString("TCI_ISV_VALUE");
	public static String TCI_ISV_SPEED=KL_Config.getString("TCI_ISV_SPEED");
	public static double TCI_SECTION_RATE_VALUE=Double.parseDouble(KL_Config.getString("TCI_SECTION_RATE_VALUE"));
	public static double TCI_INTERSECTION_RATE_VALUE=Double.parseDouble(KL_Config.getString("TCI_INTERSECTION_RATE_VALUE"));
    public static int READ_TPI_TIME=Integer.parseInt(KL_Config.getString("READ_TPI_TIME"));
	public static String FORECAST_FIVE_PARAM=KL_Config.getString("FORECAST_FIVE_PARAM");
	public static String FORECAST_TEN_PARAM=KL_Config.getString("FORECAST_TEN_PARAM");
	public static String FORECAST_FIFTEEN_PARAM=KL_Config.getString("FORECAST_FIFTEEN_PARAM");
	public static String FORECAST_THIRTY_PARAM=KL_Config.getString("FORECAST_THIRTY_PARAM");
	public static String FORECAST_SPEED_ZONE=KL_Config.getString("FORECAST_SPEED_ZONE");
	public static int FORECAST_ZONE=Integer.parseInt(KL_Config.getString("FORECAST_ZONE"));
	
	public static int FUSION_CALCU_CYCLE=Integer.parseInt(KL_Config.getString("FUSION_CALCU_CYCLE"));
    //#读取TPI历史时间天数
    public static String READ_TPI_DAYTIME=KL_Config.getString("READ_TPI_DAYTIME");
	public static boolean GPS_IS_RECEIVE = KL_Config.getString("GPS_IS_RECEIVE").equals("true")?true:false;
	public static boolean DETECT_IS_RECEIVE = KL_Config.getString("DETECT_IS_RECEIVE").equals("true")?true:false;
	public static String AID_PARAMETER_ZONE=KL_Config.getString("AID_PARAMETER_ZONE");
	public static boolean AID_IS_CALCULATE = KL_Config.getString("AID_IS_CALCULATE").equals("true")?true:false;
	public static boolean FCD_IS_CALCULATE = KL_Config.getString("FCD_IS_CALCULATE").equals("true")?true:false;
	public static int GPS_DELAY_ZONE = Integer.parseInt(KL_Config.getString("GPS_DELAY_ZONE"));
	public static String AID_CAL_CARNUM = KL_Config.getString("AID_CAL_CARNUM");
	public static int FCD_MAX_SPEED = Integer.parseInt(KL_Config.getString("FCD_MAX_SPEED"));
	public static String AID_PEAK_TIME=KL_Config.getString("AID_PEAK_TIME");
	public static int AID_CALCU_FCDSPEEDPARAM = Integer.parseInt(KL_Config.getString("AID_CALCU_FCDSPEEDPARAM"));
	public static double AID_CALCU_SPEEDRATIOPARAM=Double.parseDouble(KL_Config.getString("AID_CALCU_SPEEDRATIOPARAM"));
	
	public static boolean FCD_IS_HISSUPPLY = KL_Config.getString("FCD_IS_HISSUPPLY").equals("true")?true:false;
	public static String FCD_HISSUPPLY_K1=KL_Config.getString("FCD_HISSUPPLY_K1");
	public static double FCD_HISSUPPLY_K2=Double.parseDouble(KL_Config.getString("FCD_HISSUPPLY_K2"));
}
