package com.swmaestro.etbike.activity.map;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.swmaestro.etbike.utils.location.MyLocationManager;

public class MyMapMarker extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;
	private int color;
	String TAG = "MyMapMarker";
	Intent intent;

	public MyMapMarker(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	public MyMapMarker(Drawable defaultMarker, Context context, Intent intent) {
		this(defaultMarker);
		this.context = context;
		this.intent = intent;

	}
	
	public MyMapMarker(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		this.context = context;

	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

	}

	public void addOverlay(OverlayItem item, Drawable draw, int color) {

		setColor(color);
		item.setMarker(draw);
		mOverlays.add(item);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	@Override
	protected boolean onTap(int index) {
		MyLocationManager mlm = new MyLocationManager(context);
		GeoPoint gp = mOverlays.get(index).getPoint();
//		String detail = mlm.getDetailLocationByCoordinate(gp);
		Log.e(TAG + " on Tap", "latitude" + gp.getLatitudeE6());
		Log.e(TAG + " on Tap", "longitude" + gp.getLongitudeE6());
		
		Log.e(TAG + " on Tap", "latitude" +"" + gp.getLatitudeE6()/1E6);
		Log.e(TAG + " on Tap", "longitude" + gp.getLongitudeE6()/1E6);
		
		final Double latitude = gp.getLatitudeE6()/1E6;
		final Double longitude = gp.getLongitudeE6()/1E6;
		final String location = mlm.getDetailLocationByCoordinate(latitude + "", longitude + "");
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("다음 위치로 설정하시겠습니까?");
		dialog.setMessage(location + " 가 맞습니까?");
		dialog.setPositiveButton("확인", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("location", location);
				intent.putExtra("latitude", latitude + "");
				intent.putExtra("longitude", longitude + "");
				((Activity)context).setResult(-1,intent);
				((Activity)context).finish();
				
				
			}
		});
		dialog.setNegativeButton("취소", null);
		dialog.show();
		return true;
	}
}
