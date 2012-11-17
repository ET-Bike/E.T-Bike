package com.swmaestro.etbike.activity;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.swmaestro.etbike.activity.dialog.DialogManager;
import com.swmaestro.etbike.activity.listview.MyDynamicListAdapter;
import com.swmaestro.etbike.network.NetworkManager;
import com.swmaestro.etbike.serverobject.MyBikeBoard;
import com.swmaestro.object.WorkVectors;

public class ShareBikeActivity extends TabActivity {

	TabHost mTab;
	NetworkManager nm;
	WorkVectors wv;
	DialogManager dm;

	ArrayList<MyBikeBoard> shareBikeListAL;
	MyDynamicListAdapter mdla;
	ListView shareBikeListLV;

	Context context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mTab = getTabHost();
		LayoutInflater inflater = LayoutInflater.from(this);

		inflater.inflate(R.layout.sharebike, mTab.getTabContentView(), true);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.sharebiketitlebar);

		Drawable locaDraw = getResources().getDrawable(R.drawable.loca);
		Drawable mapDraw = getResources().getDrawable(R.drawable.map);
		Drawable searchDraw = getResources().getDrawable(R.drawable.search);

		mTab.addTab(mTab.newTabSpec("viewByList")
				.setIndicator("현재위치에서 찾기", locaDraw)
				.setContent(R.id.shareBikeByLVLL));
		mTab.addTab(mTab.newTabSpec("viewByMap")
				.setIndicator("지도로 찾기", mapDraw)
				.setContent(R.id.shareBikeByMapLL));
		mTab.addTab(mTab.newTabSpec("viewBySearch")
				.setIndicator("검색으로 찾기", searchDraw)
				.setContent(R.id.shareBikeBySearchLL));

		for (int i = 0; i < 3; i++) {
			TextView text = (TextView) mTab.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			text.setTextColor(Color.BLACK);
			mTab.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.parseColor("#efefef"));

		}

		wv = new WorkVectors();
		context = this;
		dm = new DialogManager(context);

		shareBikeListAL = new ArrayList<MyBikeBoard>();

		mdla = new MyDynamicListAdapter(context, shareBikeListAL,
				MyDynamicListAdapter.VIEW_TYPE_MYBIKE);

		shareBikeListLV = (ListView) findViewById(R.id.shareBikeLV);
		shareBikeListLV.setAdapter(mdla);

		findViewById(R.id.addBikeBtnsharebike).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(context, RegisterBike.class));

					}
				});

		findViewById(R.id.filterBikesharebike).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dm.getFilterDialog(shareBikeListAL, mdla).show();

					}
				});

		mTab.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub

				if (tabId.equals("viewByList")) {
					wv.put(WorkVectors.MY_BIKE_LIST, "ArrayList<MyBikeBoard>",
							shareBikeListAL);
					new NetworkManager(wv, mHandler,
							NetworkManager.COMM_GET_SHRE_BIKE_LIST_INFOS)
							.start();
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#5f4444"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#efefef"));
				} else if (tabId.equals("viewByMap")) {
					startActivity(new Intent(context,
							FindLocationActivity.class));
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#5f4444"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#efefef"));
				} else if (tabId.equals("viewBySearch")) {
					mTab.getTabWidget().getChildAt(0)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(1)
							.setBackgroundColor(Color.parseColor("#efefef"));
					mTab.getTabWidget().getChildAt(2)
							.setBackgroundColor(Color.parseColor("#5f4444"));

				}
			}
		});
		
		shareBikeListLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				dm.getShareBikeDialog(shareBikeListAL.get(position)).show();
			}
		});

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == NetworkManager.COMM_GET_SHRE_BIKE_LIST_INFOS) {
				Toast.makeText(context, "자전거 정보를 받아 왓습니다.", Toast.LENGTH_LONG)
						.show();
				mdla.notifyDataSetChanged();
			}
		}
	};

}
