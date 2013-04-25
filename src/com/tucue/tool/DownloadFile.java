package com.tucue.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadFile {
	/**
	 * 下载图片
	 */
	public static Bitmap getImgByURL(String urlString){
//		System.out.println(urlString);
		HttpGet httpGet = new HttpGet(urlString);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				InputStream is = httpResponse.getEntity().getContent();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        byte[] b = new byte[1024];
		        int len = 0;

		        while ((len = is.read(b, 0, 1024)) != -1) 
		        {
		         baos.write(b, 0, len);
		         baos.flush();
		        }
		        byte[] bytes = baos.toByteArray();
		        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
			}else{
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	
	public static String downXML(String urlString){		
		try {
			HttpGet httpGet = new HttpGet(urlString);	
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String string = null;
				string = EntityUtils.toString(httpResponse.getEntity());
		    	if(string == null || string.trim().equals("")){
		    		return null;
		    	} 	
		    	string = new String(string.getBytes(),"UTF-8");	
		    	return string;			
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
		/*	
		StringBuffer sb = new StringBuffer();
		String line = null;
		//用于下载的字符流
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			URL url = new URL(urlString);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));//gb2312
			while ((line = buffer.readLine()) != null) {
			//	System.out.println(line);
				try{
					sb.append(line);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();*/
	}
}
