/**
 * 
 */
package com.tucue.tool;

import java.util.ArrayList;
import java.util.List;

import com.product.Product;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/*
 * 类功能描述：异步下载图片
 */

public class DownLoadImageTask extends AsyncTask<Void, Void, Void> {
	/**
	 * 下载图片的集合
	 */
	private List<Product> list = null;
	private DataStation dataStation = null;
	private boolean flag = false;
	private int index = -1;
	
	public DownLoadImageTask(){
		dataStation = DataStation.getDataStation();
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public void setList(List<Product> list){
		this.list = new ArrayList<Product>();
		this.list.addAll(list);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Bitmap bitmap = null;
		int i = 0;
		
		for(Product product : list){
			String imgPath = product.getProductImgPath(); 
			String productID = product.getProductID();
			bitmap = dataStation.getBitmap(productID);
			//如果，flag=true就结束循环，也就是结束线程
			if(flag){
				break;
			}
			if(bitmap == null || bitmap.equals(dataStation.getDefaultBitmap())){
				bitmap = DownloadFile.getImgByURL(imgPath);
				if(bitmap == null){
					continue;
				}
				bitmap = Bitmap.createBitmap(bitmap);
				dataStation.setBitmap(productID, bitmap);
			}
			
			i++;
			if(i == 3){
				System.out.println("jichun");
				publishProgress();
				i = 0;
			}
		}
		flag = true;
		publishProgress();
	
		return null;
	} 
	 
	@Override
	protected void onProgressUpdate(Void... params) {
		/*
    	if(adapter != null){
    		adapter.notifyDataSetChanged();
    	}
    	*/
		HandlerUpdateUI.UpdateGUI();
		if(flag){
			System.out.println("Remove Task");
			DownLoadImgTaskController.getController().addTaskNum(index);
		}
    }
}
