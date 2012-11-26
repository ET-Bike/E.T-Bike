package com.swmaestro.etbike.activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.paran.animation.demo.app.animation.PathButton;
import com.paran.animation.demo.app.animation.PathButtonAnimation;
import com.swmaestro.etbike.activity.dialog.DialogManager;
import com.swmaestro.etbike.activity.listview.MyDynamicListAdapter;
import com.swmaestro.etbike.activity.listview.MyListAdapter;
import com.swmaestro.etbike.activity.map.MyCourseOverlayItem;
import com.swmaestro.etbike.network.NetworkManager;
import com.swmaestro.etbike.network.object.MyBikeBoard;
import com.swmaestro.object.WorkVectors;
import com.swmaestro.variable.Variable;

public class MyProfileActivity extends TabActivity {

	TabHost mTab;

	ArrayList<MyBikeBoard> myBikeListAL;
	MyDynamicListAdapter MyBikeListMDLA;
	ListView myBikeListLV;
	
	ArrayList<MyBikeBoard> myDealListAL;
	MyDynamicListAdapter myDealListMDLA;
	ListView myDealListLV;
	
	ArrayList<MyCourseOverlayItem> myCourseAL;
	MyListAdapter myCourseMLA;
	ListView myCourseLV;

	Context context;

	public static final int VIEW_TYPE_SEPARATOR = 0;
	public static final int VIEW_TYPE_MY_BIKE = 1;

	NetworkManager nm;

	WorkVectors wv;

	DialogManager dm;

	String TAG = "MyProfileActivity";
	
	ImageView myImgIV;
	
	private ArrayList<PathButton> buttons;
	private Button plus_button;
	private ImageView plus;
	PathButtonAnimation pathButtonAnimation;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mTab = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);

		inflater.inflate(R.layout.myprofile, mTab.getTabContentView(), true);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.myprofiletitlebar);

		Drawable myBikeDraw = getResources().getDrawable(R.drawable.my_bike_list_icon);
		Drawable tradeBikeDraw = getResources().getDrawable(R.drawable.trade_list_icon);
		Drawable partiDraw = getResources().getDrawable(R.drawable.participation_icon);

		mTab.addTab(mTab.newTabSpec("myBikeList").setIndicator("내자전거", myBikeDraw).setContent(R.id.myBikeListLL));
		mTab.addTab(mTab.newTabSpec("dealList").setIndicator("거래내역", tradeBikeDraw).setContent(R.id.dealListLL));
		mTab.addTab(mTab.newTabSpec("myJoinList").setIndicator("같이타요", partiDraw).setContent(R.id.myJoinListLL));
		
		myImgIV = (ImageView)findViewById(R.id.myprofileTitleBarMyImgIV);
		
		
		
		for (int i = 0; i < 3; i++) {
			TextView text = (TextView) mTab.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
			text.setTextColor(Color.BLACK);

			mTab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#efefef"));

		}

		dm = new DialogManager(this);
		wv = new WorkVectors();
		new NetworkManager(wv, mHandler,NetworkManager.COMM_DOWN_LOAD_MY_PROFILE_IMAGE).start();

		/*
		 * set list view
		 */
		initLV();

		/*
		 * listview가 선택됬을떄 이벤트 정의
		 */
		/*
		 * first tab init
		 */
		wv.put(WorkVectors.MY_BIKE_LIST, "ArrayList<MyBikeBoard>",myBikeListAL);
		new NetworkManager(wv, mHandler, NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS).start();
		changeTabColor(mTab, 0, 3,"#efefef" , "#5f4444");
		/*
		 * plus button set
		 */
		plus_button = (Button) findViewById(R.id.myprofile_plus_button);
		plus = (ImageView) findViewById(R.id.myprofile_plus);
		buttons = new ArrayList<PathButton>();		

		PathButton button = (PathButton) findViewById(R.id.myprofile_GoToHomePathBtn);		
		buttons.add(button);

		button = (PathButton) findViewById(R.id.myprofile_SceneWithBikePathBtn);		
		buttons.add(button);

		button = (PathButton) findViewById(R.id.myprofile_RideWithMePathBtn);		
		buttons.add(button);
		
		button = (PathButton) findViewById(R.id.myprofile_ShareYourBikePathBtn);
		buttons.add(button);
		
		
		pathButtonAnimation = new PathButtonAnimation(buttons, this, plus_button, plus);
		findViewById(R.id.myprofile_viewGroup).setOnTouchListener(pathButtonAnimation.myTouchListener);
		
		myBikeListLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// TODO Auto-generated method stub
				Log.e(TAG + " onitemclicklistener", "bikeimgpath " + myBikeListAL.get(position).getBikeImagePath());
				//
				wv.put(WorkVectors.SELECTED_MY_BIKE, "MyBikeBoard",
						myBikeListAL.get(position));
				new NetworkManager(wv, mHandler,
						NetworkManager.COMM_GET_MY_BIKE_IMG).start();
				dm.getMyBikeDialog(myBikeListAL.get(position)).show();

			}
		});
		
		myDealListLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// TODO Auto-generated method stub
//				Log.e(TAG + " onitemclicklistener", "bikeimgpath " + myDealListAL.get(position).getBikeImagePath());
//				//
				wv.put(WorkVectors.SELECTED_MY_BIKE, "MyBikeBoard",	myDealListAL.get(position));
				new NetworkManager(wv, mHandler,NetworkManager.COMM_GET_MY_DEAL_IMG).start();
				Log.e(TAG + " myDealListLV", "myDealAL size = " + myDealListAL.size());
				Log.e(TAG + " myDealListLV", "myDealAL size = " + myDealListAL.get(position).getContent());
				dm.getDealListDialog(myDealListAL.get(position)).show();

			}
		});
		

		mTab.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				

				if (tabId.equals("myBikeList")) {
					wv.put(WorkVectors.MY_BIKE_LIST, "ArrayList<MyBikeBoard>",myBikeListAL);
					new NetworkManager(wv, mHandler, NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS).start();
					changeTabColor(mTab, 0, 3,"#efefef" , "#5f4444");
				} else if (tabId.equals("dealList")) {
					wv.put(WorkVectors.MY_BIKE_LIST, "ArrayList<MyBikeBoard>",myBikeListAL);
					new NetworkManager(wv, mHandler, NetworkManager.COMM_GET_SHRE_BIKE_LIST_INFOS).start();
					changeTabColor(mTab, 1, 3,"#efefef" , "#5f4444");
				} else {
					
					changeTabColor(mTab, 2, 3,"#efefef" , "#5f4444");
					if(Variable.MCOI != null) {
						myCourseAL.add(Variable.MCOI);
						myCourseMLA.notifyDataSetChanged();
					}
					
//					c
				}

			}
		});

		context = this;

	}
	
	private void initLV() {
		myBikeListAL = new ArrayList<MyBikeBoard>();
		MyBikeListMDLA = new MyDynamicListAdapter(this, myBikeListAL,MyDynamicListAdapter.VIEW_TYPE_MYBIKE);
		myBikeListLV = (ListView) findViewById(R.id.myBikeLV);
		myBikeListLV.setAdapter(MyBikeListMDLA);
		
		myDealListAL = new ArrayList<MyBikeBoard>();
		myDealListMDLA = new MyDynamicListAdapter(this, myDealListAL,MyDynamicListAdapter.VIEW_TYPE_MYBIKE);
		myDealListLV = (ListView) findViewById(R.id.myProfileDealLV);
		myDealListLV.setAdapter(myDealListMDLA);
		
		myCourseAL = new ArrayList<MyCourseOverlayItem>();
		myCourseMLA = new MyListAdapter(this,R.layout.myjoinlistitem, myCourseAL, MyListAdapter.MY_COURSE);
		myCourseLV = (ListView) findViewById(R.id.myProfileMyJoinLV);
		myCourseLV.setAdapter(myCourseMLA);
		
		
	}
	
	private void changeTabColor(TabHost mTab, int tabPosition, int tabSize,
			String defTabColorRGB, String chagingTabColorRGB) {

		for (int i = 0; i < tabSize; i++) {
			if (tabPosition == i) {
				mTab.getTabWidget()
						.getChildAt(i)
						.setBackgroundColor(
								Color.parseColor(chagingTabColorRGB));

			} else {
				mTab.getTabWidget().getChildAt(i)
						.setBackgroundColor(Color.parseColor(defTabColorRGB));
			}

		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String handlerTAg = "handler";
			if (msg.what == NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS) {
				Toast.makeText(context, "내 자전거 정보를 받아 왓습니다.", Toast.LENGTH_LONG).show();
				MyBikeListMDLA.notifyDataSetChanged();
			} else if (msg.what == NetworkManager.COMM_GET_MY_BIKE_IMG || msg.what == NetworkManager.COMM_GET_MY_DEAL_IMG) {
				MyBikeBoard mbb = (MyBikeBoard) wv.getData(WorkVectors.SELECTED_MY_BIKE);
				Log.e(TAG + " " + handlerTAg + " getmybike", "strbieimg = "	+ mbb.getStrBikeImagePath());
				dm.setDownloadedIV(mbb);
			}else if(msg.what == NetworkManager.COMM_DOWN_LOAD_MY_PROFILE_IMAGE) {
				String imgPath = (String)wv.getData(WorkVectors.MY_PROFLE_IMG_PATH);
				myImgIV.setImageURI(Uri.parse(imgPath));
				
			}else if(msg.what == NetworkManager.COMM_GET_SHRE_BIKE_LIST_INFOS) {
				Log.e(TAG + " handler " + " get share Bike List", "selected bike " + Variable.MY_SELECTED_BIKE);
				if(Variable.MY_SELECTED_BIKE != 0) {
					
					myDealListAL.add(myBikeListAL.get(Variable.MY_SELECTED_BIKE));
					myDealListMDLA.notifyDataSetChanged();
				}
			}
				
		}
	};

}

