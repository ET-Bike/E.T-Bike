package com.swmaestro.etbike.activity.dialog;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.text.style.BulletSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.swmaestro.etbike.activity.R;
import com.swmaestro.etbike.activity.listview.MyDynamicListAdapter;
import com.swmaestro.etbike.activity.listview.MyListAdapter;
import com.swmaestro.etbike.activity.listview.object.RegisterItem;
import com.swmaestro.etbike.serverobject.MyBikeBoard;
import com.swmaestro.etbike.serverobject.Reply;
import com.swmaestro.etbike.utils.location.MyLocationManager;
import com.swmaestro.object.WorkVectors;
import com.swmaestro.variable.Variable;

//import android.view.View.OnClickListener;

public class DialogManager {

	Context context;
	AlertDialog.Builder builder;
	AlertDialog ad;
	LinearLayout ll;
	MyLocationManager mlm;

	public DialogManager(Context context) {
		this.context = context;
		this.builder = new AlertDialog.Builder(context);
		this.mlm = new MyLocationManager(context);

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
						wv.put(WorkVectors.BIKE_TYPE,
								WorkVectors.BIKE_TYPE_MOUNTAIN);
						value = Variable.KOR_BIKE_TYPE_MOUNTAIN;
					} else if (position == 1) {
						wv.put(WorkVectors.BIKE_TYPE,
								WorkVectors.BIKE_TYPE_COMMUTE);
						value = Variable.KOR_BIKE_TYPE_COMMUTE;
					} else if (position == 2) {
						wv.put(WorkVectors.BIKE_TYPE,
								WorkVectors.BIKE_TYPE_PLAYER);
						value = Variable.KOR_BIKE_TYPE_PLAYER;
					}
					mainDialPosition = 0;

				} else if (dialType.equals(WorkVectors.DIAL_TYPE_TRADE)) {
					if (position == 0) {
						wv.put(WorkVectors.TRADE_TYPE,
								WorkVectors.TRADE_TYPE_DIRECT_DEAL);
						value = Variable.KOR_TRADE_TYPE_DIRECT_DEAL;
					} else if (position == 1) {
						wv.put(WorkVectors.TRADE_TYPE,
								WorkVectors.TRADE_TYPE_DELIEVERY);
						value = Variable.KOR_TRADE_TYPE_DELIEVERY;
					}
					mainDialPosition = 1;

				} else if (dialType.equals(WorkVectors.DIAL_TYPE_SHARE)) {
					if (position == 0) {
						wv.put(WorkVectors.SHARE_TYPE,
								WorkVectors.SHARE_TYPE_RENT);
						value = Variable.KOR_SHARE_TYPE_RENT;
					} else if (position == 1) {
						wv.put(WorkVectors.SHARE_TYPE,
								WorkVectors.SHARE_TYPE_DONATION);
						value = Variable.KOR_SHARE_TYPE_DONATION;
					} else if (position == 2) {
						wv.put(WorkVectors.SHARE_TYPE,
								WorkVectors.SHARE_TYPE_SELL);
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

		TextView titleTV = (TextView) ll.findViewById(R.id.titleTVmyBikeDialog);
		TextView shareTV = (TextView) ll
				.findViewById(R.id.shareTypeTVmyBikeDialog);
		TextView ownerTV = (TextView) ll.findViewById(R.id.ownerTVmyBikeDialog);
		TextView dealWithTV = (TextView) ll
				.findViewById(R.id.dealWithTVmyBikeDialog);
		TextView detailLocTV = (TextView) ll
				.findViewById(R.id.detailLocationTVmyBikeDialog);

		titleTV.setText(mbb.getContent());
		shareTV.setText(mbb.getShareType());
		ownerTV.setText(mbb.getWriter());
		dealWithTV.setText(mbb.getDealWith());

		String lati = mbb.getLati();
		String longi = mbb.getLongi();

		detailLocTV.setText(mlm.getDetailLocationByCoordinate(lati, longi));

		builder.setView(ll).setTitle("자전거의 세부내용입니다.")
				.setPositiveButton("확인", null);
		ad = builder.create();
		return ad;

	}

	public AlertDialog getShareBikeDialog(final MyBikeBoard mbb, final Handler mHandler) {

		/*
		 * define view
		 */
		ll = (LinearLayout) View.inflate(context, R.layout.sharebikedialog,
				null);

		TextView titleTV = (TextView) ll
				.findViewById(R.id.titleTVshareBikeDialog);
		TextView shareTV = (TextView) ll
				.findViewById(R.id.shareTypeTVshareBikeDialog);
		TextView ownerTV = (TextView) ll
				.findViewById(R.id.ownerTVshareBikeDialog);
		TextView detailLocTV = (TextView) ll
				.findViewById(R.id.detailLocationTVshareBikeDialog);
		// Button likeButton =
		// (Button)ll.findViewById(R.id.likeBtnshareBikeDialog);
		final TextView likeTV = (TextView) ll
				.findViewById(R.id.likeCountTVshareBikeDialog);

		final TextView commentTV = (TextView) ll
				.findViewById(R.id.commentCountshareBikeDialog);

		final ArrayList<String> commentAL = new ArrayList<String>();
		final ArrayAdapter<String> commentAA = new ArrayAdapter<String>(
				context, android.R.layout.simple_list_item_1, commentAL);
		// ListView commentLV =
		// (ListView)ll.findViewById(R.id.commentLVshareBikeDialog);
		// commentLV.setAdapter(commentAA);

		titleTV.setText(mbb.getContent());
		shareTV.setText(mbb.getShareType());
		ownerTV.setText(mbb.getWriter());

		String lati = mbb.getLati();
		String longi = mbb.getLongi();

		detailLocTV.setText(mlm.getDetailLocationByCoordinate(lati, longi));

		if (mbb.getReplies() == null) {
			commentTV.setText(0 + "");
		} else {
			commentTV.setText(mbb.getReplies().size() + "");
		}

		boolean likeFlag = false;

		ll.findViewById(R.id.likeBtnshareBikeDialog).setOnClickListener(
				new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						// int likeCount = Integer.parseInt(likeTV.getText()
						// .toString());
						// if()
						// if(mbb.getLikeCount() == null) {
						// likeTV.setText(1 + "");
						// }else {
						int likeNum = mbb.getLikeCount() + 1;
						String likeString = likeNum + "";
						likeTV.setText(likeString);
						// }

					}
				});

		ll.findViewById(R.id.commentIVshareBikeDialog).setOnClickListener(
				new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// if (mbb.getReplies() == null) {
						// return;
						// }
						// for (int i = 0; i < mbb.getReplies().size(); i++) {
						// Iterator<Reply> replies = mbb.getReplies()
						// .iterator();
						// while (replies.hasNext()) {
						// Reply reply = replies.next();
						// String content = reply.getWriter() + ":"
						// + reply.getMessage() + "("
						// + reply.getUpdatedTime() + ")";
						// commentAL.add(content);
						// }
						// }
						// commentAA.notifyDataSetChanged();
						ad.dismiss();
						getCommentDialog(mbb, mHandler).show();

					}
				});

		// builder�� view ���̱�
		builder.setView(ll).setTitle("자전거의 세부내용입니다.")
				.setPositiveButton("거래요청", null).setNegativeButton("취소", null);
		ad = builder.create();
		return ad;

	}

	private AlertDialog getCommentDialog(final MyBikeBoard mbb, Handler mHandler) {

		ll = (LinearLayout) View.inflate(context, R.layout.commentdialog, null);

		ImageView iv = (ImageView) ll
				.findViewById(R.id.BikeIVcommentDialog);
		ListView lv = (ListView) ll.findViewById(R.id.commentLVcommentDialog);
		EditText et = (EditText) ll.findViewById(R.id.commentETcommentDialog);
		Button btn = (Button) ll.findViewById(R.id.commentBtncommentDialog);

		ArrayList<String> al = new ArrayList<String>();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, al);

		if (mbb.getReplies() != null) {

			for (int i = 0; i < mbb.getReplies().size(); i++) {
				Iterator<Reply> replies = mbb.getReplies().iterator();
				while (replies.hasNext()) {
					Reply reply = replies.next();
					String content = reply.getWriter() + ":"
							+ reply.getMessage() + "(" + reply.getUpdatedTime()
							+ ")";
					al.add(content);
				}
			}

		}

		lv.setAdapter(aa);

		Bitmap bm = BitmapFactory.decodeFile(mbb.getStrBikeImagePath());
		iv.setImageBitmap(bm);

		
		builder.setView(ll);
		ad = builder.create();
		return ad;

	}

	// private AlertDialog getFilterDialog(final MyBikeBoard mbb) {
	//
	// return
	// }

	public AlertDialog getFilterDialog(final ArrayList<MyBikeBoard> mal,
			final MyDynamicListAdapter sbla) {

		/*
		 * define view
		 */
		// ll = (LinearLayout) View.inflate(context, R.layout.mybikedialog,
		// null);
		//
		// TextView lv = (ListView) ll.findViewById(R.id.myBikeLVmyBikeDialog);
		//
		// final ArrayList<String> al = new ArrayList<String>();
		//
		// al.add(WorkVectors.SHARE_TYPE_DONATION);
		// al.add(WorkVectors.SHARE_TYPE_RENT);
		// al.add(WorkVectors.SHARE_TYPE_SELL);
		//
		// // wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_RENT);
		// // value = Variable.KOR_SHARE_TYPE_RENT;
		// // } else if (position == 1) {
		// // wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_DONATION);
		// // value = Variable.KOR_SHARE_TYPE_DONATION;
		// // } else if (position == 2) {
		// // wv.put(WorkVectors.SHARE_TYPE, WorkVectors.SHARE_TYPE_SELL);
		// // value = Variable.KOR_SHARE_TYPE_SELL;
		// final ArrayList<MyBikeBoard> nmal = new ArrayList<MyBikeBoard>();
		//
		// lv.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// // TODO Auto-generated method stub
		// for (int i = 0; i < mal.size(); i++) {
		//
		// if (al.get(position).equals(mal.get(i).getShareType())) {
		// nmal.add(mal.get(i));
		// }
		// }
		//
		// mal.clear();
		// for (int i = 0; i < nmal.size(); i++) {
		// mal.add(nmal.get(i));
		// }
		// sbla.notifyDataSetChanged();
		// ad.dismiss();
		//
		// }
		//
		// });
		//
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
		// android.R.layout.simple_list_item_1, al);
		// lv.setAdapter(adapter);
		//
		// builder.setView(ll);
		ad = builder.create();
		return ad;

	}

	public void setMyBikeImgDialog(final MyBikeBoard mbb) {

		ImageView iv = (ImageView) ll.findViewById(R.id.myBikeIVmyBikeDialog);
//		Bitmap bm = BitmapFactory.decodeFile(mbb.getStrBikeImagePath());
//		iv.setImageBitmap(bm);
		iv.setImageURI(Uri.parse(mbb.getStrBikeImagePath()));

	}

	public void setshareBikeImgDialog(final MyBikeBoard mbb) {

		ImageView iv = (ImageView) ll
				.findViewById(R.id.shareBikeIVshareBikeDialog);
//		Bitmap bm = BitmapFactory.decodeFile(mbb.getStrBikeImagePath());
//		iv.setImageBitmap(bm);
		iv.setImageURI(Uri.parse(mbb.getStrBikeImagePath()));

	}

	public AlertDialog getMapDialog(String lati, String longi) {

		ad = builder.create();
		return ad;
	}

}
