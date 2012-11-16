package com.swmaestro.etbike.activity.dialog;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WpsInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.swmaestro.etbike.activity.R;

import com.swmaestro.etbike.activity.listview.MyDynamicListAdapter;
import com.swmaestro.etbike.activity.listview.MyListAdapter;
import com.swmaestro.etbike.activity.listview.object.RegisterItem;
import com.swmaestro.etbike.serverobject.MyBikeBoard;
import com.swmaestro.object.WorkVectors;
import com.swmaestro.variable.Variable;

public class DialogManager {

	Context context;
	AlertDialog.Builder builder;
	AlertDialog ad;
	LinearLayout ll;

	public DialogManager(Context context) {
		this.context = context;
		this.builder = new AlertDialog.Builder(context);

	}

	public AlertDialog getRegisterDialog(final WorkVectors wv,
			final ArrayList<RegisterItem> mal, final MyListAdapter mla) {

		// �� �並 ���̰� ad ��
		/*
		 * ll = (LinearLayout) View.inflate(this, R.layout.actiondialog, null);
		 * builder.setView(ll); ad = builder.create(); ad.show(); break;
		 */

		/*
		 * define view
		 */

		final LinearLayout ll = (LinearLayout) View.inflate(context,
				R.layout.listviewdialog, null);

		final String dialType = (String) wv.getData(WorkVectors.DIAL_TYPE);

		// ArrayAdapter<String> aa = new ArrayAdapter<String>(context,
		// textViewResourceId);

		ListView lv = (ListView) ll.findViewById(R.id.listViewLV);
		final ArrayList<String> al = new ArrayList<String>();

		if (dialType.equals(WorkVectors.DIAL_TYPE_BIKE)) {
			al.add("산악용");
			al.add("출퇴근용");
			al.add("선수용");
		} else if (dialType.equals(WorkVectors.DIAL_TYPE_TRADE)) {
			al.add("직거래");
			al.add("택배");
		} else if (dialType.equals(WorkVectors.DIAL_TYPE_SHARE)) {
			al.add("대여");
			al.add("기부");
			al.add("판매");

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, al);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String value = null;
				int mainDialPosition = 0;
				if (dialType.equals(WorkVectors.DIAL_TYPE_BIKE)) {
					if (position == 0) {
						wv.put(WorkVectors.BIKE_TYPE, WorkVectors.BIKE_TYPE_MOUNTAIN);
						value = Variable.KOR_BIKE_TYPE_MOUNTAIN;
					} else if (position == 1) {
						wv.put(WorkVectors.BIKE_TYPE, WorkVectors.BIKE_TYPE_COMMUTE);
						value = Variable.KOR_BIKE_TYPE_COMMUTE;
					} else if (position == 2) {
						wv.put(WorkVectors.BIKE_TYPE, 	WorkVectors.BIKE_TYPE_PLAYER);
						value = Variable.KOR_BIKE_TYPE_PLAYER;
					}
					mainDialPosition = 0;

				} else if (dialType.equals(WorkVectors.DIAL_TYPE_TRADE)) {
					if (position == 0) {
						wv.put(WorkVectors.TRADE_TYPE, WorkVectors.TRADE_TYPE_DIRECT_DEAL);
						value = Variable.KOR_TRADE_TYPE_DIRECT_DEAL;
					} else if (position == 1) {
						wv.put(WorkVectors.TRADE_TYPE, WorkVectors.TRADE_TYPE_DELIEVERY);
						value = Variable.KOR_TRADE_TYPE_DELIEVERY;
					}
					mainDialPosition = 1;

				} else if (dialType.equals(WorkVectors.DIAL_TYPE_SHARE)) {
					if (position == 0) {
						wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_RENT);
						value = Variable.KOR_SHARE_TYPE_RENT;
					} else if (position == 1) {
						wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_DONATION);
						value = Variable.KOR_SHARE_TYPE_DONATION;
					} else if (position == 2) {
						wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_SELL);
						value = Variable.KOR_SHARE_TYPE_SELL;
					}
					mainDialPosition = 2;
				}
				mal.get(mainDialPosition).value = value;
				mla.notifyDataSetChanged();
				ad.dismiss();

			}

		});

		// builder�� view ���̱�
		builder.setTitle("자전거의 세부사항을 입력해주세요.").setView(ll);
		ad = builder.create();

		return ad;

	}

	public AlertDialog getCostDialog(final WorkVectors wv) {

		final LinearLayout ll = (LinearLayout) View.inflate(context,
				R.layout.costdialog, null);

		// builder�� view ���̱�
		builder.setTitle("자전거의 비용을 입력주세요.").setView(ll)
				.setPositiveButton("확인", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						TextView tv1 = (TextView) ll
								.findViewById(R.id.timeCostET);
						TextView tv2 = (TextView) ll
								.findViewById(R.id.dayCostET);
						TextView tv3 = (TextView) ll
								.findViewById(R.id.weekCostET);

						String text1 = tv1.getText().toString();
						String text2 = tv2.getText().toString();
						String text3 = tv3.getText().toString();

						if (text1.equals("") || text2.equals("")
								|| text3.equals("")) {
							return;
						} else {
							wv.put(WorkVectors.COST_TIME, text1);
							wv.put(WorkVectors.COST_DAY, text2);
							wv.put(WorkVectors.COST_WEEK, text3);
						}

					}
				});
		ad = builder.create();
		return ad;

	}

	public AlertDialog getMyBikeDialog(final MyBikeBoard mbb) {

		/*
		 * define view
		 */
		ll = (LinearLayout) View.inflate(context, R.layout.mybikedialog, null);

		ListView lv = (ListView) ll.findViewById(R.id.myBikeLVmyBikeDialog);

		ArrayList<String> al = new ArrayList<String>();

		al.add("자전거 타입: " + mbb.getBikeType());
		al.add("거래 타입: " + mbb.getTradeType());
		al.add("공유 타입: " + mbb.getShareType());
		al.add("세부 사항 : " + mbb.getContent());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, al);
		lv.setAdapter(adapter);

		// builder�� view ���̱�
		builder.setView(ll);
		ad = builder.create();
		return ad;

	}

	public AlertDialog getFilterDialog(final ArrayList<MyBikeBoard> mal,
			final MyDynamicListAdapter sbla) {

		/*
		 * define view
		 */
		ll = (LinearLayout) View.inflate(context, R.layout.mybikedialog, null);

		ListView lv = (ListView) ll.findViewById(R.id.myBikeLVmyBikeDialog);

		final ArrayList<String> al = new ArrayList<String>();

		al.add(WorkVectors.SHARE_TYPE_DONATION);
		al.add(WorkVectors.SHARE_TYPE_RENT);
		al.add(WorkVectors.SHARE_TYPE_SELL);

		// wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_RENT);
		// value = Variable.KOR_SHARE_TYPE_RENT;
		// } else if (position == 1) {
		// wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_DONATION);
		// value = Variable.KOR_SHARE_TYPE_DONATION;
		// } else if (position == 2) {
		// wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_SELL);
		// value = Variable.KOR_SHARE_TYPE_SELL;
		final ArrayList<MyBikeBoard> nmal = new ArrayList<MyBikeBoard>();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mal.size(); i++) {

					if (al.get(position).equals(mal.get(i).getShareType())) {
						nmal.add(mal.get(i));
					}
				}

				mal.clear();
				for (int i = 0; i < nmal.size(); i++) {
					mal.add(nmal.get(i));
				}
				sbla.notifyDataSetChanged();
				ad.dismiss();

			}

		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, al);
		lv.setAdapter(adapter);

		builder.setView(ll);
		ad = builder.create();
		return ad;

	}

	public void setBikeImgDialog(final MyBikeBoard mbb) {

		ImageView iv = (ImageView) ll.findViewById(R.id.myBikeIVmyBikeDialog);
		Bitmap bm = BitmapFactory.decodeFile(mbb.getStrBikeImagePath());
		iv.setImageBitmap(bm);

	}

	public AlertDialog getMapDialog(String lati, String longi) {

		ad = builder.create();
		return ad;
	}

	

}
