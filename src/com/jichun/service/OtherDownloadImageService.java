package com.jichun.service;


import java.util.ArrayList;
import java.util.List;

import com.product.Product;
import com.tucue.tool.DataStation;
import com.tucue.tool.DownLoadImgTaskController;
import com.tucue.tool.DownloadFile;
import com.tucue.tool.HandlerUpdateUI;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;

public class OtherDownloadImageService extends Service{
	private Thread workThread=null;
	private static ArrayList<Product> imageList = new ArrayList<Product>();
	protected int num = 0;
	private DownLoadImgTaskController taskController = null;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	  // 添加需要下载图片的编号、地址
	public static void addImageRequer(Product  imageinfo){	
		imageList.add(imageinfo);
	}
	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("Service-onStart");
		super.onStart(intent, startId);
		
		 if (!workThread.isAlive()){
	    	  workThread.start();
	      }
	}
	
	public void onCreate() {
		System.out.println("Service-onCreate");
		super.onCreate();
		workThread = new Thread(null,backgroudWork,"workThread"); // 创建服务线程
		taskController = DownLoadImgTaskController.getController();
	}
	
	public void onDestroy() {
		super.onDestroy();System.out.println("Service-onDestroy");
//		 Toast.makeText(this, "服务启动停止", Toast.LENGTH_SHORT).show();     
	     workThread.interrupt();                                                              
	}
	
	private Runnable backgroudWork = new Runnable(){
		@Override
		public void run() {
			
			try {		
				while(!Thread.interrupted()){
					DownloadImg();
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.out.println("thread stop"+e.getMessage());
			}
		}	
	};
	
	private void DownloadImg(){
//System.out.println(imageList.size());
		//System.out.println("Num Begin:" + ++num);
		//int i = 0;
		while(imageList.size()>0){
			List<Product> list = new ArrayList<Product>();
			int size = imageList.size();
			for(int i = size - 1;i >= 0;i--){
				list.add(imageList.get(i));
			}
			
			taskController.setList(list);
			taskController.execute();
			imageList.clear();
		}
	}
}
