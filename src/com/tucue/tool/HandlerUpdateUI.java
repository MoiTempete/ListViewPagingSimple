package com.tucue.tool;

import android.os.Handler;
import android.widget.BaseAdapter;

public class HandlerUpdateUI {
	public static BaseAdapter adapter = null;
	public static Handler handlerUI = new Handler();
	
	public static void setAdapter(BaseAdapter adapters){
		adapter = adapters;
	}
	
	public static void UpdateGUI(){	
			handlerUI.post(RefreshLable);	 
		}
	 private static Runnable RefreshLable = new Runnable(){
			public void run(){
				if(adapter != null){
					adapter.notifyDataSetChanged();
				}
			}
		};
}
