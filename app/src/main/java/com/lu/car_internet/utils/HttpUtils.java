package com.lu.car_internet.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.lu.car_internet.beans.CarMessage;


public class HttpUtils {
private static String KEY = "6965eced61a69e02464c5e31ccf0063a";  
    
    private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");
    public static String addressToGPS(String address) {  
    	String data = ""; 
    	try {
        String url = String.format("http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s", KEY, address); 
    	URL myURL = null; 
    	URLConnection httpsConn = null; 
    	try { 
    	myURL = new URL(url); 
    	} catch (MalformedURLException e) { 
    	e.printStackTrace(); 
    	} 
    	InputStreamReader insr = null;
    	BufferedReader br = null;
    	httpsConn = (URLConnection) myURL.openConnection();//不使用代理
    	if (httpsConn!= null) { 
    	insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
    	br = new BufferedReader(insr); 
    	
    	String line = null; 
    	while((line= br.readLine())!=null){
    	data+=line;
    	//System.out.println(data);
    	} 
    	        /*    Matcher matcher = pattern.matcher(data);  
    	            if (matcher.find() && matcher.groupCount() == 2) {  
    	                double[] gps = new double[2];  
    	                gps[0] = Double.valueOf(matcher.group(1));  
    	                gps[1] = Double.valueOf(matcher.group(2));  
    	                return gps;  
    	            }*/
    	}
        }catch (Exception e) {
        	e.printStackTrace(); 
         return null;
 }
       
		return data;
}
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public static String submitPostData(Map<String,String>params,String encode,String type){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String urlPath="http://119.29.231.31/index.php?controller=user&method=regist";
        try {
            URL url = null;
            byte[] data=getRequestData(params,encode).toString().getBytes();
        //if(type.equals("login")){
       // urlPath="http://192.168.191.1:8080/project_car/user!"+type+".action?";
        // urlPath="http://192.168.43.190:8080/project_car/user!"+type+".action?";
          urlPath="http://139.199.73.19/project_car/user!"+type+".action?";
         url = new URL(urlPath);
       //     }
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",String.valueOf(data.length));
            OutputStream outputStream=httpURLConnection.getOutputStream();
            outputStream.write(data);
            int response=httpURLConnection.getResponseCode();
            if(response==HttpURLConnection.HTTP_OK){
                InputStream inptStream=httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);
            }else{
                return "无法连接网络";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "-1";
        }

    }
    public static StringBuffer getRequestData(Map<String,String> params, String encode){
        StringBuffer stringBuffer=new StringBuffer();
        try {
            for(Map.Entry<String, String> entry:params.entrySet()){

                stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),encode)).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    public static String dealResponseResult(InputStream inputStream){
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byte[] data=new byte[1024];
        int len=0;
        try {
            while((len=inputStream.read(data))!=-1){
                byteArrayOutputStream.write(data,0,len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

public static String getJsonContent(String url_path,String encode){

        String jsonString = "";
        try {
            URL url = new URL(url_path);            
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(4000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);  //从服务器获得数据
             
            int responseCode = connection.getResponseCode();            
             
            if (200 == responseCode) {
                jsonString = changeInputStream(connection.getInputStream(),encode);

            }
             
        } catch (Exception e) {
        	return "-1";
        	
        }
         

        return jsonString;
    }

  
      private static String changeInputStream(InputStream inputStream , String encode) throws IOException {
    // TODO Auto-generated method stub
    String  jsonString = null;
     
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    int len = 0;
    while((len=inputStream.read(data))!=-1){
        outputStream.write(data, 0, len);
    }
    jsonString = new String(outputStream.toByteArray(), encode);
    inputStream.close();
     
     
    return jsonString;
}
    public static String jsonToObj(String jsonStr) throws Exception {   
        JSONObject jsonObject = new JSONObject(jsonStr);  
        String fatherName = jsonObject.getString("geocodes");  
        JSONArray childs= jsonObject.getJSONArray("geocodes");  
        int length = childs.length();
        //System.out.println( length);
        String gps=null;  
        for (int i = 0; i < length; i++) {  
            jsonObject = childs.getJSONObject(i);
            //Double.valueOf(jsonObject.getString("location"));
            gps=jsonObject.getString("city");
            gps=gps+","+jsonObject.getString("location");
        }  
        return gps;
    } 
    
   public static String jsoncity(String jsonStr)throws Exception {
       JSONObject jsonObject = new JSONObject(jsonStr);
        String resultobject=jsonObject.getString("regeocode");
//       JSONObject formatobject=resultobject.getJSONObject("formatted_address");
       JSONObject formatted_address = new JSONObject(resultobject);
       String addressComponent = formatted_address.getString("addressComponent");
       JSONObject cityobject= new JSONObject(addressComponent);
       String city=cityobject.getString("city");
//       System.out.print(jsonStr+"");


       return city;
   }
  public static String jsontemperature(String jsonStr)throws Exception {
      JSONObject obj = new JSONObject(jsonStr);
            /*获取返回状态码*/
      jsonStr = obj.getString("resultcode");
            /*如果状态码是200说明返回数据成功*/
      if (jsonStr != null && jsonStr.equals("200")) {
          jsonStr = obj.getString("result");
          //此时result中数据有多个key,可以对其key进行遍历,得到对个属性
          obj = new JSONObject(jsonStr);
          //今日温度对应的key是today
          String temperaturejsonStr = obj.getString("today");
          JSONObject obj1 = new JSONObject(temperaturejsonStr);
          //今日温度对应当key是temperature
          temperaturejsonStr = obj1.getString("temperature");
          String weather = obj1.getString("weather");
          String currentjsonStr = obj.getString("sk");
          obj = new JSONObject(currentjsonStr);
          currentjsonStr = obj.getString("temp");
          String updatetime = obj.getString("time");
          return temperaturejsonStr+","+currentjsonStr+","+updatetime+","+weather;
      }
      return jsonStr;

  }
//  public static List<CarMessage> getListCarMessage(String jsonStr) throws Exception{
//      List<CarMessage> mlists = new ArrayList<CarMessage>();
//      //嵌套数组遍历
//      JSONObject carJsonobj = new JSONObject(jsonStr);
//      jsonStr =carJsonobj.getString("code");
//      if(jsonStr !=null && jsonStr.equals("0")){
//          JSONArray carList = carJsonobj.getJSONArray("cars");
//          for (int i = 0; i < carList.length(); i++) {
//              JSONObject item = carList.getJSONObject(i);
//              String carid = item.getString("carId");
//              String carBrand = item.getString("carBrand");
//              String carFlag = item.getString("carFlag");
//              String carType = item.getString("carType");
//              String carNumber = item.getString("carNumber");
//              String carMotorNumber = item.getString("carMotorNumber");
//              String carBodylevel = item.getString("carBodylevel");
//              mlists.add(new CarMessage(carid,carBrand,carFlag,carType,carNumber,carMotorNumber,carBodylevel));
//          }
//      }else{
//
//      }
//      return mlists;
//  }




}
