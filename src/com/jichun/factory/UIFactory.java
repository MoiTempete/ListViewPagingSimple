/**
 * 
 */
package com.jichun.factory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jichun.activit.R;
import com.tucue.tool.DataStation;

public class UIFactory {
    public static LinearLayout createFootView(Activity activity){
    	LayoutParams WClayoutParams =new LinearLayout.LayoutParams(
    			LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	LinearLayout layout = new LinearLayout(activity);
 	   //设置布局 水平方向
 		layout.setOrientation(LinearLayout.HORIZONTAL);
 		 //进度条
 		ProgressBar progressBar = new ProgressBar(activity);
 		 //进度条显示位置
 		progressBar.setPadding(0, 0, 15, 0);
 		
 		layout.addView(progressBar, WClayoutParams);
 		
 		TextView textView = new TextView(activity);
 		textView.setText("加载中...");
 		textView.setGravity(Gravity.CENTER_VERTICAL);
 		
 		layout.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT));
 		layout.setGravity(Gravity.CENTER);
 		
 		LinearLayout loadingLayout = new LinearLayout(activity);
 		loadingLayout.addView(layout, WClayoutParams);
 		loadingLayout.setGravity(Gravity.CENTER);
 		
 		return loadingLayout;
    }

	public static ProgressDialog createProgress(Activity activity,String message){
		ProgressDialog m_pDialog = new ProgressDialog(activity);
		// 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
     // 设置ProgressDialog 标题
        m_pDialog.setTitle("提示");
       
        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage(message);

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(false);
        
        return m_pDialog;
    }
	
	public static Dialog createAlertDialog(String alertString,Activity activity){
    	Dialog dialog = new AlertDialog.Builder(activity).setTitle("提示")
    		.setMessage(alertString).setPositiveButton("确定", 
    		new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					paramDialogInterface.cancel();
				}
			}).create();
    	
    	return dialog;
    }
}
