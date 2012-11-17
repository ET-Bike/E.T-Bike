package com.swmaestro.etbike.activity.listview;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
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

	public MyListAdapter(Context context, int layout, ArrayList<RegisterItem> al) {
		this.context = context;
		this.layout = layout;
		this.registerAL = al;
		this.li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm = context.getPackageManager();

	}

	public MyListAdapter(Context context, int layout,
			ArrayList<MyBikeBoard> al, int type) {
		this.context = context;
		this.layout = layout;
		this.bikeAL = al;
		this.li = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pm = context.getPackageManager();

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return registerAL.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return registerAL.get(position).key;
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

			writerIV.setImageURI(Uri.parse(mbb.getStrMyImgPath()));
			writerTV.setText(mbb.getWriter());
			sceneIV.setImageURI(Uri.parse(mbb.getStrBikeImagePath()));
			likeTV.setText(mbb.getLikeCount());
			if (mbb.getReplies() != null) {
				commentTV.setText(mbb.getReplies().size());
			} else {
				commentTV.setText("0");
			}
		}

		return convertView;

	}

}