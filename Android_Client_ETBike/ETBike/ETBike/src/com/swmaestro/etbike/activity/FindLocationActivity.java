package com.swmaestro.etbike.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.swmaestro.etbike.activity.listview.object.LocationItem;
import com.swmaestro.etbike.activity.map.MyDynamicLocationOverlay;
import com.swmaestro.etbike.activity.map.MyMapMarker;
import com.swmaestro.etbike.utils.location.MyLocationManager;
import com.swmaestro.etbike.utils.location.UtilityProvider;

public class FindLocationActivity extends MapActivity {

	MapView mv;
	MyDynamicLocationOverlay mdlo;
	EditText et;
	MyLocationManager mlm;

	ListView lv;
	ArrayList<LocationItem> lal;
	ArrayList<String> al;
	ArrayAdapter<String> adapter;

	Context context;
	String TAG = "FindLocationActivity";
	
	Intent intent;
	MyMapMarker mapMarker;
	
	
	

	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		new UtilityProvider(this).initTitleBar(R.layout.findlocation, R.layout.findlocationtitlebar);
		
		mlm = new MyLocationManager(this);

		mv = (MapView) findViewById(R.id.findLocationMV);
		et = (EditText) findViewById(R.id.findLocationETtitleBar);
		
		context = this;
		/*
		 * location fix
		 */
		mdlo = new MyDynamicLocationOverlay(this, mv);
		mdlo.enableMyLocation();
		mdlo.enableCompass();
		mdlo.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mv.getController().animateTo(mdlo.getMyLocation());
			}
		});
		mv.getOverlays().add(mdlo);

		lv = (ListView) findViewById(R.id.findLocationLV);
		al = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
		lv.setAdapter(adapter);

		lal = new ArrayList<LocationItem>();
		
		intent = new Intent();
		
			

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				intent = new Intent();
				String location = lal.get(position).location;
				Double latitude = lal.get(position).latitude;
				Double longitude = lal.get(position).longitude;
				
				Log.e(TAG +"listview click", location);

				intent.putExtra("location", location);
				intent.putExtra("latitude", latitude + "");
				
				intent.putExtra("longitude", longitude + "");
				setResult(RESULT_OK, intent);

				finish();

			}

		});

		findViewById(R.id.findLocationBtntitleBar).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String text = et.getText().toString();
						if (text.equals("")) {
							// Toast.makeText(context, text, duration)
							return;
						}
						lal = mlm.getDetailLocationListByLocation(text);
						al.clear();
						for (int i = 0; i < lal.size(); i++) {
							al.add(lal.get(i).location);
						}
						addOverlay(lal);
						animateLocation(lal.get(0).latitude, lal.get(0).longitude);
						adapter.notifyDataSetChanged();

					}
				});
	}

	private void addOverlay(ArrayList<LocationItem> al) {
		
		Drawable stDraw = context.getResources().getDrawable(R.drawable.map_departure_icon);		
		mapMarker = new MyMapMarker(stDraw, context, intent);	
//		mapMarker.setIntent(intent);	
		stDraw.setBounds(0, 0, stDraw.getIntrinsicWidth(),	stDraw.getIntrinsicHeight());
		
		for (int i = 0; i < al.size(); i++) {
			int ilati = (int) (al.get(i).latitude * 1E6);
			int ilongi = (int) (al.get(i).longitude * 1E6);
			GeoPoint gp = new GeoPoint(ilati, ilongi);
			mapMarker.addOverlay(new OverlayItem(gp, "", ""), stDraw, Color.BLACK);			
			
		}
		
		if (mapMarker != null) {
			mv.getOverlays().add(mapMarker);
		}

	}

	private void animateLocation(Double lati, Double longi) {
		
		int ilati = (int) (lati * 1E6);
		int ilongi = (int) (longi * 1E6);
		GeoPoint gp = new GeoPoint(ilati, ilongi);

		mv.getController().setZoom(17);
		mv.getController().animateTo(gp);

	}

	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onDestory() {
		super.onDestroy();
		mdlo.disableCompass();
		mdlo.disableMyLocation();
	}

}
