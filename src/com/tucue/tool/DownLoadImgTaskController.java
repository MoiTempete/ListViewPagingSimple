/**
 * 
 */
package com.tucue.tool;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.product.Product;

/*
 * 类功能描述：下载图片任务线程控制类
 */

public class DownLoadImgTaskController {
	private final static int LIST_SIZE = 4;
	private List<Product> list = null;
	private static DownLoadImgTaskController controller = null;
	private List<DownLoadImageTask> taskList = null;
	
	static{
		controller = new DownLoadImgTaskController();
	}
	
	
	private DownLoadImgTaskController(){
		list = new ArrayList<Product>();
		taskList = new ArrayList<DownLoadImageTask>(LIST_SIZE);
		for(int i = 0;i < LIST_SIZE;i++){
			taskList.add(null);
		}
	}
	
	public static DownLoadImgTaskController getController(){
		return controller;
	}
	
	public void setList(List<Product> list){
		this.list.addAll(list);
	}
	
	//每创建一个downLoadImageTask类就放进taskList里，最多能放4个task，如果，
	//满了就不下载这一批图片，等到正在执行的task结束以后，创建新的task下载list里的图片
	public void execute(){
		int index = -1;
		for(int i = 0;i < LIST_SIZE;i++){
			if(taskList.get(i) == null){
				index = i;
			}
		}
		
		if(index == -1){
			return;
		}
		
		DownLoadImageTask task = new DownLoadImageTask();
		task.setList(this.list);
		taskList.add(index,task);
		task.setIndex(index);
		task.execute();
		
		this.list.clear();
	}
	
	//DownLoadImageTask结束的时候会调用这个方法，讲taskList里的一个位置空出来
	//并看看list里有无数据，有的话就在创建一个task来下载图片
	public void addTaskNum(int index){
		taskList.set(index, null);
		if(list.size() > 0){
			execute();
		}
	}
	
	//因为，AsyncTask线程是关不掉的(调用task.cancel()也关不掉)，所以自己设置
	//一个Flag来关掉线程
	public void clearTasks(){
		System.out.println("Clear Tasks");
		for(int i = 0;i < LIST_SIZE;i++){
			DownLoadImageTask task = taskList.get(i);
			if(task != null && task.getStatus() != AsyncTask.Status.FINISHED){
				task.setFlag(true);
				taskList.set(i, null);
			}
		}
	}
}




