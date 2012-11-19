package com.swmaestro.etbike.activity.listview;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swmaestro.etbike.activity.R;
import com.swmaestro.etbike.activity.dialog.DialogManager;
import com.swmaestro.etbike.activity.listview.object.RegisterItem;
import com.swmaestro.etbike.serverobject.MyBikeBoard;

public class MyListAdapter extends BaseAdapter {

	Context context;
	LayoutInflater li;
	ArrayList<RegisterItem> registerAL;
	ArrayList<MyBikeBoard> bikeAL;
	PackageManager pm;
	int layout;
	int viewType;
	String TAG = "MyListAdapter";
	
	public static final int SCENE_VIEW = 0;
	public static final int REGISTER_VIEW = 1;

	public MyListAdapter(Context context, int layout, ArrayList<RegisterItem> al) {
		this.context = context;
		this.layout = layout;
		this.registerAL = al;
		this.li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm = context.getPackageManager();
		this.viewType = REGISTER_VIEW;

	}

	public MyListAdapter(Context context, int layout,
			ArrayList<MyBikeBoard> al, int type) {
		this.context = context;
		this.layout = layout;
		this.bikeAL = al;
		this.li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm = context.getPackageManager();
		this.viewType = type;

	}

	public int getCount() {
		// TODO Auto-generated method stubif)
//		if(S)
		if(viewType == SCENE_VIEW) {
			return bikeAL.size();
		}else {
			return registerAL.size();	
		}
		
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(viewType == SCENE_VIEW) {
			return bikeAL.get(position).getBikeImagePath();
		}else {
			return registerAL.get(position).key;	
		}
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {
			convertView = li.inflate(layout, parent, false);
		}

		if (R.layout.registerfeatureitem == layout) {

			TextView ttv1 = (TextView) convertView
					.findViewById(R.id.keyTVregisterFeature);
			ttv1.setText(registerAL.get(position).key);

			TextView ttv2 = (TextView) convertView
					.findViewById(R.id.valueTVregisterFeature);
			ttv2.setText(registerAL.get(position).value);

		} else if (R.layout.sharebikeitem == layout) {
			ImageView iv = (ImageView) convertView
					.findViewById(R.id.shareBikeIV);
			TextView titleTV = (TextView) convertView
					.findViewById(R.id.shareBikeTitleTV);
			TextView peopleTV = (TextView) convertView
					.findViewById(R.id.shareBikePeopleValueTV);
			Button btn = (Button) convertView
					.findViewById(R.id.findLocationBtnShareBikeItem);

			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DialogManager dm = new DialogManager(context);
					// dm.getMapDialog(lati, longi).show(;

				}
			});

		} else if (R.layout.sceneitem == layout) {

			MyBikeBoard mbb = bikeAL.get(position);

			ImageView writerIV = (ImageView) convertView
					.findViewById(R.id.sceneWriterPicIVSceneitem);
			TextView writerTV = (TextView) convertView
					.findViewById(R.id.sceneWriterTVSceneitem);
			ImageView sceneIV = (ImageView) convertView
					.findViewById(R.id.scenePictureIVsceneItem);
			TextView likeTV = (TextView) convertView
					.findViewById(R.id.sceneLikeCountTV);
			TextView commentTV = (TextView) convertView
					.findViewById(R.id.sceneCommentCountTV);
			
			TextView detailTV = (TextView)convertView.findViewById(R.id.sceneDetailTVsceneItem);

			writerIV.setBackgroundResource(mbb.getStrMyImgResource());
			writerTV.setText(mbb.getWriter());
			sceneIV.setImageURI(Uri.parse(mbb.getStrBikeImagePath()));
//			mbb.getStrBikeImagePath()
			Log.e(TAG + " sceneview", "scene path = " + mbb.getStrBikeImagePath());
//			String sLikeCount = mbb.getLikeCount() +
			likeTV.setText((mbb.getLikeCount() + ""));
//			if (mbb.getReplies() != null) {
//				commentTV.setText(mbb.getReplies().size());
//			} else {
//				commentTV.setText("0");
//			}
			commentTV.setText(mbb.getRepliesCount() + "");
			detailTV.setText(mbb.getContent());
		}

		return convertView;

	}

}