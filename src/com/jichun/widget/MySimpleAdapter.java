/**
 * 
 */
package com.jichun.widget;

import java.util.ArrayList;
import java.util.List;

import com.jichun.activit.R;
import com.jichun.service.OtherDownloadImageService;
import com.product.Product;
import com.tucue.tool.DataStation;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MySimpleAdapter extends BaseAdapter {
	private List<Product> list = null;
	private LayoutInflater inflater = null;
	private DataStation dataStation = null;
	
	public MySimpleAdapter(Context context,Bitmap defaultBitmap){
		list = new ArrayList<Product>();
		inflater = LayoutInflater.from(context);
		dataStation = DataStation.getDataStation();
	}
	
	public void addItem(Product product){
		list.add(product);
	}
	
	public void addItemList(List<Product> subList){
		list.addAll(subList);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return list.size();
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Product getItem(int position) {
		return list.get(position);
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void clearList(){
		list.clear();
	}

	public List<Product> getAdapterList(){
		return list;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_product_layout, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.productImg = (ImageView)convertView.findViewById(R.id.productImage);
			viewHolder.productName = (TextView)convertView.findViewById(R.id.productName);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Product product = list.get(position);
		
		Bitmap bitmap = dataStation.getBitmap(product.getProductID());
		if(bitmap == null){
			System.out.println(product.getProductID());
			bitmap = dataStation.getDefaultBitmap();
			OtherDownloadImageService.addImageRequer(new Product(product.getProductID(),(product.getProductImgPath())));
		}
		
		viewHolder.productImg.setImageBitmap(bitmap);
		String name = product.getProductName();
		viewHolder.productName.setText(name);
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView productImg = null;
		TextView productName = null;
	}
}
