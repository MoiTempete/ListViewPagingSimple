/**
 * 
 */
package com.tucue.tool;
import android.graphics.Bitmap;

public class DataStation{
	private static DataStation station = null;
	private BitmapCache imageCacheMap = null;
	private Bitmap defaultBitmap = null;
	private final static int CACHE_IMG_SIZE = 15;
	
	static{
		station = new DataStation();
	}
	
	private DataStation(){
		imageCacheMap = new BitmapCache(CACHE_IMG_SIZE);
	}
	
	public static DataStation getDataStation(){
		return station;
	}
	
	public void setBitmap(String productID,Bitmap bitmap){
		this.imageCacheMap.put(productID,bitmap);
	}
	
	public Bitmap getBitmap(String productID){
		return this.imageCacheMap.get(productID);
	}

	public Bitmap getDefaultBitmap() {
		return defaultBitmap;
	}

	public void setDefaultBitmap(Bitmap defaultBitmap) {
		if(this.defaultBitmap == null){
			this.defaultBitmap = defaultBitmap;
		}
	}
}
