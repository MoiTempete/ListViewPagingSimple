package com.jichun.activity;

import java.util.ArrayList;
import java.util.List;

import com.jichun.activit.R;
import com.jichun.factory.UIFactory;
import com.jichun.service.OtherDownloadImageService;
import com.jichun.widget.MySimpleAdapter;
import com.product.Product;
import com.tucue.tool.DataStation;
import com.tucue.tool.DownLoadImgTaskController;
import com.tucue.tool.HandlerUpdateUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class IndexActivity extends Activity {
	private ListView listView = null;				
	private MySimpleAdapter adapter = null;
	private int lastItem = 0;				
	private int totalPage = 0;
	private int pageNum = 0;
	private boolean footFlag = false;
	private LinearLayout footViewLayout = null;	
	private DataStation dataStation = null;				//图片存储类，单例模式
	private List<Product> productsList = null;			//所有商品存在这个list里，分页显示这里面的商品信息
	private DownLoadImgTaskController taskController = null;	//下载图片任务类
	private final static int PAGESIZE = 12; 			//每页显示商品数量
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        taskController = DownLoadImgTaskController.getController();
        this.initParam();
        this.initAdapter();
        this.initListView();
        initProuctsList();
        
		if(!footFlag && totalPage <= 1){
			listView.removeFooterView(footViewLayout);
			footFlag = true;
		}
		
		nextPage();
    }
    
    public void initParam(){
    	dataStation = DataStation.getDataStation();
    	dataStation.setDefaultBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon));
    }
    
    public void initAdapter(){
    	adapter = new MySimpleAdapter(this,dataStation.getDefaultBitmap());
    }
    
    private void initProuctsList(){
    	productsList = new ArrayList<Product>();
    	for(int i = 0;i < 120;i++){
    		Product product = new Product();
    		product.setProductID(i + "");
    		product.setProductImgPath("http://www.51soutu.net:8080/product/originalImage/127764/20110326/1/9739834200.jpg");
    		product.setProductName("2011新款韩版 手提女包 手挽女包");
    		dataStation.setBitmap(i+"", dataStation.getDefaultBitmap());
    		productsList.add(product);
    	}
    	pageNum = 1;
    	int size = productsList.size();
    	if(size % PAGESIZE == 0){
    		totalPage = size / PAGESIZE;
    	}else{
    		totalPage = size / PAGESIZE + 1;
    	}
    }
    
    public void initListView(){
    	listView = (ListView)findViewById(R.id.searchList);
    	footViewLayout = UIFactory.createFootView(this);
    	//addFooterView方法一定要在setAdapter方法之前执行
    	listView.addFooterView(footViewLayout);
    	footFlag = false;
    	listView.setAdapter(adapter);
    	
    	listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
				if(lastItem == adapter.getCount() && paramInt == SCROLL_STATE_IDLE){
					nextPage();
				}
			}
			
			@Override
			public void onScroll(AbsListView v, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) { 
				lastItem = firstVisibleItem + visibleItemCount - 1;
			}
		});
    }
    
    private void nextPage(){
    	List<Product> list = new ArrayList<Product>();
    	if(!footFlag && pageNum == totalPage){
			listView.removeFooterView(footViewLayout);
			footFlag = true;
		}
    	
    	if(pageNum > totalPage){
    		return;
    	}
    	
    	if(pageNum == totalPage){
    		list = productsList.subList((pageNum - 1) * PAGESIZE, productsList.size());
    	}else{
    		list = productsList.subList((pageNum - 1) * PAGESIZE, pageNum * PAGESIZE);
    	}
    		
    	adapter.addItemList(list);
    	adapter.notifyDataSetChanged();
    	Toast.makeText(this, "第" + pageNum++ + "页，总共" + totalPage + "页", Toast.LENGTH_SHORT).show();
    	taskController.setList(list);
    	taskController.execute();
    	listView.setSelection(lastItem);
    }
    
    private Intent serviceIntent = null;
	private void StartService() {
		HandlerUpdateUI.setAdapter(this.adapter);
		serviceIntent = new Intent(this, OtherDownloadImageService.class);
		serviceIntent.putExtra("mag", 2);
		startService(serviceIntent);
	}
	
	@Override
	protected void onResume() {
		StartService();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		stopService(serviceIntent);
	}
	
	//Activity关闭的时候一定要停止所有下载图片线程
	private void cancelAllTasks(){
		taskController.clearTasks();
	}
	
	public void onDestroy(){
		super.onDestroy();
		cancelAllTasks();
	}
}