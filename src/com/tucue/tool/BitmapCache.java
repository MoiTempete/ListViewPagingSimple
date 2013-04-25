/**
 * 
 */
package com.tucue.tool;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.Bitmap;
import android.text.GetChars;

/*
 * 原理：分别使用两个HashMap来缓存图片，一个是强引用map，另一个是软引用map
 * 		 图片首先存在强引用map中，如果，强引用map满了就按照先进先出规则将第一个图片移到
 *  	 软引用map，软引用map里的图片会按照内存情况自行随即清空图片
 */

public class BitmapCache {
	private Map<String, Bitmap> map = null;
	private Map<String, SoftReference<Bitmap>> softMap = null;
	private int size = 0;
	private List<String> list = null;
	
	public BitmapCache(int size){
		this.size = size;
		map = new HashMap<String, Bitmap>();
		softMap = new HashMap<String, SoftReference<Bitmap>>();
		list = new ArrayList<String>();
	}
	
	public void put(String id,Bitmap bitmap){
		if(!list.contains(id)){
			list.add(id);
			if(list.size() > size){
				String indexValue = list.get(0);
				softMap.put(indexValue, new SoftReference<Bitmap>(map.get(indexValue)));
				map.put(indexValue, null);
				list.remove(0);
			}
		}
		
		map.put(id, bitmap);
	}
	
	public Bitmap get(String id){
		Bitmap bitmap = map.get(id);
		if(bitmap == null){
			return getSoftBitmap(id);
		}
		
		return map.get(id);
	}
	
	private Bitmap getSoftBitmap(String id){
		SoftReference<Bitmap> bitmapReference = softMap.get(id);
		if(bitmapReference != null){
			return bitmapReference.get();
		}
		
		return null;
	}
}
















