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

public class UploadFile {
	/**
	 * 
	  * 方法描述：下载xml
	 */
	public static String downXML(String urlString){
		HttpGet httpGet = new HttpGet(urlString);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return EntityUtils.toString(httpResponse.getEntity());
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
	
	/**
	 * 下载图片
	 */
	public static Bitmap getImgByURL(String urlString){
		HttpGet httpGet = new HttpGet(urlString);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
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

}

























