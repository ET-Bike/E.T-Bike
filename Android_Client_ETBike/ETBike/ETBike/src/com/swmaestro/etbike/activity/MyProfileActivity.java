package com.swmaestro.etbike.activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.swemaestro.etbike.dao.DBManager;
import com.swmaestro.etbike.activity.dialog.DialogManager;
import com.swmaestro.etbike.activity.listview.MyDynamicListAdapter;
import com.swmaestro.etbike.network.NetworkManager;
import com.swmaestro.etbike.serverobject.MyBikeBoard;
import com.swmaestro.object.WorkVectors;

public class MyProfileActivity extends TabActivity {

	TabHost mTab;

	ArrayList<MyBikeBoard> myBikeListAL;
	MyDynamicListAdapter mdla;
	ListView myBikeListLV;

	Context context;

	public static final int VIEW_TYPE_SEPARATOR = 0;
	public static final int VIEW_TYPE_MY_BIKE = 1;

	DBManager dbm;

	NetworkManager nm;

	WorkVectors wv;

	DialogManager dm;

	String TAG = "MyProfileActivity";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mTab = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);

		inflater.inflate(R.layout.myprofile, mTab.getTabContentView(), true);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.myprofiletitlebar);

		Drawable myBikeDraw = getResources().getDrawable(
				R.drawable.my_bike_list_icon);
		Drawable tradeBikeDraw = getResources().getDrawable(
				R.drawable.trade_list_icon);
		Drawable partiDraw = getResources().getDrawable(
				R.drawable.participation_icon);

		mTab.addTab(mTab.newTabSpec("myBikeList")
				.setIndicator("내자전거", myBikeDraw).setContent(R.id.myBikeListLL));
		mTab.addTab(mTab.newTabSpec("dealList")
				.setIndicator("거래내역", tradeBikeDraw)
				.setContent(R.id.dealListLL));
		mTab.addTab(mTab.newTabSpec("myJoinList")
				.setIndicator("같이타요", partiDraw).setContent(R.id.myJoinListLL));

		for (int i = 0; i < 3; i++) {
			TextView text = (TextView) mTab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			text.setTextColor(Color.BLACK);

			mTab.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#efefef"));

		}

		dbm = new DBManager(context);
		dm = new DialogManager(this);
		wv = new WorkVectors();
		nm = new NetworkManager(wv, mHandler,
				NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS);

		/*
		 * set list view
		 */

		myBikeListAL = new ArrayList<MyBikeBoard>();
		// myBikeListAL.add(new MyBikeBoard("none", "wow", "wow", "wow"));
		mdla = new MyDynamicListAdapter(this, myBikeListAL,
				MyDynamicListAdapter.VIEW_TYPE_MYBIKE);

		myBikeListLV = (ListView) findViewById(R.id.myBikeLV);
		myBikeListLV.setAdapter(mdla);

		/*
		 * listview ���� ������ ����
		 */

		myBikeListLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// TODO Auto-generated method stub
				Log.e(TAG + " onitemclicklistener", "bikeimgpath "
						+ myBikeListAL.get(position).getBikeImagePath());
				//
				wv.put(WorkVectors.SELECTED_MY_BIKE, "MyBikeBoard",
						myBikeListAL.get(position));
				new NetworkManager(wv, mHandler,
						NetworkManager.COMM_GET_MY_BIKE_IMG).start();
				dm.getMyBikeDialog(myBikeListAL.get(position)).show();

			}
		});

		mTab.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				int position = 0;

				if (tabId.equals("myBikeList")) {
					wv.put(WorkVectors.MY_BIKE_LIST, "ArrayList<MyBikeBoard>",
							myBikeListAL);
					new NetworkManager(wv, mHandler,
							NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS).start();
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#5f4444"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#efefef"));
				} else if (tabId.equals("dealList")) {
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#5f4444"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#efefef"));
				} else {
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#5f4444"));
				}

			}
		});

		context = this;

		// findViewById(R.id.addBikeBtnMyProfile).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(context, RegisterBike.class));
		// }
		// });

		// findViewById(R.id.addBikeBtnMyProfile).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// startActivity(new Intent(context, RegisterBike.class));
		// }
		// });
		//
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String handlerTAg = "handler";
			if (msg.what == NetworkManager.COMM_GET_MY_BIKE_LIST_INFOS) {
				Toast.makeText(context, "내 자전거 정보를 받아 왓습니다.", Toast.LENGTH_LONG)
						.show();
				mdla.notifyDataSetChanged();
			} else if (msg.what == NetworkManager.COMM_GET_MY_BIKE_IMG) {

				MyBikeBoard mbb = (MyBikeBoard) wv
						.getData(WorkVectors.SELECTED_MY_BIKE);
				Log.e(TAG + " " + handlerTAg + " getmybike", "strbieimg = "
						+ mbb.getStrBikeImagePath());
				dm.setBikeImgDialog(mbb);
			}
		}
	};

}
