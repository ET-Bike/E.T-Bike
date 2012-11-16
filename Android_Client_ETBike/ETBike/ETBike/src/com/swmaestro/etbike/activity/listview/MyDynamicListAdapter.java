package com.swmaestro.etbike.activity.listview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.MapView;
import com.swmaestro.etbike.activity.MyProfileActivity;
import com.swmaestro.etbike.activity.R;
import com.swmaestro.etbike.serverobject.MyBikeBoard;

public class MyDynamicListAdapter extends BaseAdapter {

	Context context;
	LayoutInflater li;
	ArrayList<MyBikeBoard> al;
	MapView mv;
	boolean mapScrollFlag;
	String exQuery;
	String TAG = "MyDynamicListAdapter";
	String exShareType = "대여";
	int mdloType = 0;

	public static final int VIEW_TYPE_MYBIKE = 0;
	public static final int VIEW_TYPE_SHARE_BIKE = 1;

	public MyDynamicListAdapter(Context context, ArrayList<MyBikeBoard> al,
			int mdloType) {
		this.context = context;
		this.al = al;
		this.li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mdloType = mdloType;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return al.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return al.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public int getViewType(int position) {
		return al.get(position).getViewType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getViewTypeCount() �ٲ����
	 */
	//11-16 22:49:59.815: E/PageStremer executeMethod(8655): url = http://125.209.193.11:8080/etbike//shareBoard/getMyBikeList/doo.kim.54/

	public int getViewTypeCount() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		String strBikeImgThumbPath = al.get(position).getStrBikeImageThumbPath();
		String content = al.get(position).getContent();
		String tradeType = al.get(position).getTradeType();
		String shareType = al.get(position).getShareType();
		
		int viewType = al.get(position).getViewType();
		
		Log.e(TAG + " getView","shareType = " + shareType);
		

		if (convertView == null) {
			Log.e(TAG, "convertview null");

			int res = 0;
			if (viewType == MyProfileActivity.VIEW_TYPE_SEPARATOR) {
				res = R.layout.mdlaseparatoritem;
			} else if (viewType == MyProfileActivity.VIEW_TYPE_MY_BIKE) {
				res = R.layout.mdlamybikeitem;
			}
			convertView = li.inflate(res, parent, false);
		}
		if (mdloType == VIEW_TYPE_MYBIKE) {
			if (viewType == MyProfileActivity.VIEW_TYPE_SEPARATOR) {
				
				TextView tv = (TextView) convertView.findViewById(R.id.separatorTVMDLAview);
				tv.setText(shareType);

			} else {

				ImageView iv = (ImageView) convertView.findViewById(R.id.myBikeImgIVMyBikeItem);
				TextView tv = (TextView) convertView.findViewById(R.id.tradeTypeTVMyBikeItem);
				TextView tv1 = (TextView) convertView.findViewById(R.id.detailTVMyBikeItem);
				Log.e(TAG + " getView","strBikeThumbImg = " + strBikeImgThumbPath);
				if(strBikeImgThumbPath != null) {
					Log.e(TAG + " getView" ,"strBikeImgThumbPath = "+ strBikeImgThumbPath);
					Bitmap bm = BitmapFactory.decodeFile(strBikeImgThumbPath);
					iv.setImageBitmap(bm);	
				}
				tv.setText(tradeType);
				tv1.setText(content);
				exShareType = shareType;

			}
		}
		

		return convertView;
	}

}