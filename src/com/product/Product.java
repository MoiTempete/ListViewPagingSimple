/**
 * 
 */
package com.product;

/**
 * 文件名称：Product.java
 *
 * 版权信息：杭州图酷科技有限公司
 *
 * 创建日期：2011-2-17
 *
 * 修改历史：商品实体类
 */
/*
 * 类功能描述：
 * 作者：全基春
 */

public class Product {
	/**
	 * 商品ID
	 */
	private String productID = null;
	/**
	 * 商品名称
	 */
	private String productName = null;
	/**
	 * 商品图片URL
	 */
	private String productImgPath = null;
	
	public Product(){
		
	}
	
	public Product(String id,String imgPath){
		this.productID = id;
		this.productImgPath = imgPath;
	}
	
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImgPath() {
		return productImgPath;
	}
	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}
}
