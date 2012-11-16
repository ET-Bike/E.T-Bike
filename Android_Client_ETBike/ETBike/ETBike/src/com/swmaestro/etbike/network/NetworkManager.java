package com.swmaestro.etbike.network;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.android.FHaloManager;
import com.google.gson.Gson;
import com.swmaestro.etbike.activity.MyProfileActivity;
import com.swmaestro.etbike.serverobject.JsonResult;
import com.swmaestro.etbike.serverobject.MyBikeBoard;
import com.swmaestro.etbike.serverobject.MyBikeList;
import com.swmaestro.object.WorkVectors;
import com.swmaestro.variable.Variable;

public class NetworkManager extends Thread {
	String TAG = "NetworkManager";

	public static final int ZERO = 0;
	public static final int COMM_DOWN_LOAD_HOME_IMAGE = 1;
	public static final int COMM_DOWN_LOAD_MY_PROFILE_IMAGE = 2;
	public static final int COMM_SEND_MY_ACCOUNT_INFO = 3;
	public static final int COMM_UPLOAD_BIKE_IMG = 4;
	public static final int COMM_UPLOAD_MY_BIKE_INFOS = 5;
	public static final int COMM_GET_MY_BIKE_LIST_INFOS = 6;
	// public static final int COMM_UPLOAD_MY_BIKE_INFOS = 5;
	public static final int COMM_GET_SHRE_BIKE_LIST_INFOS = 7;
	public static final int COMM_GET_MY_BIKE_IMG = 8;

	int commandState = -1;

	WorkVectors wv;
	Handler mHandler;
	NetworkHelper nh;
	FHaloManager fhm;
	boolean threadStartFlag = false;
	
	/*
	 * url = new URL("ㅎㅎ당신이 원하는 url");
con = (HttpURLConnection)url.openConnection();
con.setRequestMethod("POST");
con.setDoOutput(true);
con.setRequestProperty("Accept-Charset", charset); 
con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
con.setInstanceFollowRedirects(false);

// Parameter Setting
con.setRequestProperty("login_id", "ㅎㅎ");
con.setRequestProperty("login_password", "ㅎㅎ");
con.setRequestProperty("x", "40");
con.setRequestProperty("y", "30");
con.connect();
[출처] Web Login Using HTTP Request in Java (POST)|작성자 세디츠
	 */
	

	public NetworkManager(WorkVectors wv, Handler mHandler, int commandState) {
		// TODO Auto-generated constructor stub
		this.wv = wv;
		this.mHandler = mHandler;
		this.commandState = commandState;
		nh = new NetworkHelper();
		// fhm =FHaloManager.getInstance();

	}

	public void setCommand(int commandState) {
		this.commandState = commandState;
	}

	public void run() {

		Log.e(TAG, "commstate ==" + commandState);
		if (threadStartFlag == false) {
			Looper.prepare();
			threadStartFlag = true;
			if (commandState == COMM_DOWN_LOAD_MY_PROFILE_IMAGE) {
				Log.e(TAG + " start", "commstate == "
						+ COMM_DOWN_LOAD_MY_PROFILE_IMAGE);
				downLoadmyProfile(wv);
			} else if (commandState == COMM_SEND_MY_ACCOUNT_INFO) {
				uploadMyAccout(wv);
			} else if (commandState == COMM_UPLOAD_MY_BIKE_INFOS) {
				uploadMyBikeInfos();
			}else if (commandState == COMM_GET_MY_BIKE_LIST_INFOS) {
				getBikeListInfos();
			} else if (commandState == COMM_GET_SHRE_BIKE_LIST_INFOS) {
				getBikeListInfos();
			} else if(commandState == COMM_GET_MY_BIKE_IMG) {
				getBikeImg();
			}
			threadStartFlag = false;
			Looper.loop();
		}

	}
	
	private void getBikeImg() {
		String mTAG = " getBikeImg";
		MyBikeBoard mbb = (MyBikeBoard)wv.getData(WorkVectors.SELECTED_MY_BIKE);
		Log.e(TAG + mTAG, "bikeimg url " + mbb.getBikeImagePath());
		Log.e(TAG + mTAG, "bikethumbimg url " + mbb.getBikeImagePathThumb());
		String fileName = nh.getFileName(mbb.getBikeImagePath());
		String strPath = Variable.MY_BIKE_DIR + fileName;
		Log.e(TAG + "getBikeImg", "fileName "+fileName);
		Log.e(TAG + "getBikeImg", "strPath "+strPath);
		if(nh.downloadImage(mbb.getBikeImagePath(), strPath)) {
			mbb.setStrBikeImagePath(strPath);
			mHandler.sendEmptyMessage(COMM_GET_MY_BIKE_IMG);
		}
	}

	private void getBikeListInfos() {

		fhm = FHaloManager.getInstance();
		String myID = fhm.getUserName();
		nh.clearGetAndPUT();
		nh.setMethodType("get");
		ArrayList<MyBikeBoard> mal = (ArrayList<MyBikeBoard>)wv.getData(WorkVectors.MY_BIKE_LIST);
		if (commandState == COMM_GET_MY_BIKE_LIST_INFOS)
			nh.setGetServUrl(Variable.SERVER_GET_MY_BIKE_LIST_URL + myID + "/");
		else if (commandState == COMM_GET_SHRE_BIKE_LIST_INFOS)
			nh.setGetServUrl(Variable.SERVER_GET_SHARE_BIKE_LIST_URL);

		String JsonStr = nh.executeHttpMethod();
		Log.e(TAG + " getMyBikeListInfos", JsonStr);
		JsonResult jr = new Gson().fromJson(JsonStr, JsonResult.class);
		ArrayList<MyBikeBoard> nmal = jr.getMyBikeList().getMyBikeBoard();
		
		sortMyBikeList(mal, nmal);
		downloadMyBikeThumbNail(mal);
		if (commandState == COMM_GET_MY_BIKE_LIST_INFOS)
			mHandler.sendEmptyMessage(COMM_GET_MY_BIKE_LIST_INFOS);
		else if (commandState == COMM_GET_SHRE_BIKE_LIST_INFOS)
			mHandler.sendEmptyMessage(COMM_GET_SHRE_BIKE_LIST_INFOS);
	}
	
	private void sortMyBikeList(ArrayList<MyBikeBoard> mal, ArrayList<MyBikeBoard> nmal) {
		String exShareType = null;
		String curShareType = null;
		
		mal.clear();
		
		for(int i = 0; i < nmal.size(); i++) {

			if(i == 0) {
				exShareType = nmal.get(i).getShareType();
				mal.add(new MyBikeBoard(exShareType, MyProfileActivity.VIEW_TYPE_SEPARATOR));
				nmal.get(i).setViewType(MyProfileActivity.VIEW_TYPE_MY_BIKE);
				mal.add(nmal.get(i));
			}else {
				curShareType = nmal.get(i).getShareType();
				if(!curShareType.equals(exShareType)) {
					mal.add(new MyBikeBoard(curShareType, MyProfileActivity.VIEW_TYPE_SEPARATOR));
				}
				nmal.get(i).setViewType(MyProfileActivity.VIEW_TYPE_MY_BIKE);
				mal.add(nmal.get(i));
				exShareType = curShareType;
				
			}
			
			
		}
	}

	private void downloadMyBikeThumbNail(ArrayList<MyBikeBoard> mal) {

		ArrayList<MyBikeBoard> al = mal;

		for (int i = 0; i < al.size(); i++) {

			String thumbNailUrl = al.get(i).getBikeImagePathThumb();
			
			if (thumbNailUrl == null) {
				al.get(i).setStrBikeImageThumbPath("none");
				continue;

			}
			Log.e(TAG + " downlaodMyBikeThumbNail", "download thumbnail = "	+ thumbNailUrl);
			String path = Variable.MY_BIKE_THUMB_DIR + nh.getThumbNailFileName(thumbNailUrl);
			if (nh.downloadImage(thumbNailUrl, path)) {
//				nh.getThumbNailFileName(thumbNailUrl);
				Log.e(TAG + " downlaodMyBikeThumbNail", "download ok");
				Log.e(TAG + " downlaodMyBikeThumbNail", "strThumbnail path = " + path);
				al.get(i).setStrBikeImageThumbPath(path);
			}

		}

	}
	
	

	// /http://125.209.193.11:8080/etbike/thumb/62/50

	private void downLoadmyProfile(WorkVectors wv) {

		fhm = FHaloManager.getInstance();
		Log.e(TAG + " download MY profile", "start");
		Log.e(TAG, "downlaod image url = " + fhm.getMyProfilePic());
		String downloadUrl = fhm.getMyProfilePic();
		String path = Variable.MY_PROFILE_DIR + nh.getFileName(downloadUrl);

		if (nh.downloadImage(downloadUrl, path)) {
			wv.put(WorkVectors.MY_PROFLE_IMG_PATH, path);
			mHandler.sendEmptyMessage(COMM_DOWN_LOAD_MY_PROFILE_IMAGE);
		}

	}

	private void uploadMyBikeInfos() {
		Boolean uploadFacebookFlag = (Boolean) wv
				.getData(WorkVectors.UPLOAD_FACEBOOK_FLAG);
		uploadMyBikePic(wv);
		uploadMyProfilePic(wv);
		uploadMyBikeInfo(wv);
		if (uploadFacebookFlag)
			uploadMyBikePicAtFeed(wv);

	}

	/*
	 * shard preference로 최초로그인 체크 이미지 업로드
	 */

	private void uploadMyAccout(WorkVectors wv) {

		fhm = FHaloManager.getInstance();
		String downloadUrl = fhm.getMyProfilePic();
		String myImageFileName = nh.getFileName(downloadUrl);
		String path = Variable.MY_PROFILE_DIR + myImageFileName;

		if (nh.downloadImage(downloadUrl, path)) {
			nh.doFileUpload(path, Variable.SERVER_IMG_URL);
		}
		// fhm = FHaloManager.getInstance();
		nh.setGetServUrl(Variable.SERVER_URL);
		nh.addGetParameter("username", fhm.getUserName());
		nh.addGetParameter("firstName", fhm.getMyFirstName());
		nh.addGetParameter("lastName", fhm.getMyLastName());
		nh.addGetParameter("password", "password");
		nh.addGetParameter("myImagePath", myImageFileName);
		nh.executeGet();
	}

	private void uploadMyBikeInfo(WorkVectors wv) {
		fhm = FHaloManager.getInstance();

		String category = "SHARE";
		String title = "sell";
		String writer = fhm.getUserName();

		String myImageName = nh.getFileName(wv
				.getData(WorkVectors.MY_PROFLE_IMG_PATH) + "");

		String bikeImageName = nh.getFileName(wv
				.getData(WorkVectors.BIKE_IMG_PATH) + "");
		Log.e(TAG + " uploadBikeInfos", "my bike img name = " + bikeImageName);
		Log.e(TAG + " uploadBikeInfos", "my img name = " + myImageName);

		String bikeType = wv.getData(WorkVectors.BIKE_TYPE) + "";
		String tradeType = wv.getData(WorkVectors.TRADE_TYPE) + "";
		String shareType = wv.getData(WorkVectors.SHARE_TYPE) + "";

		String lati = wv.getData(WorkVectors.BIKE_LATITUDE) + "";
		String longi = wv.getData(WorkVectors.BIKE_LONGITUDE) + "";
		String content = wv.getData(WorkVectors.BIKE_INFO_CONTENT) + "";

		nh.setMethodType("post");
//		nh.setGetServUrl(Variable.SERVER_BIKE_INFO_URL);
		//http://125.209.193.11:8080/etbike/boards
		nh.setGetServUrl("http://125.209.193.11:8080/etbike/addBoard");
		nh.addGetParameter("category", category);
		nh.addGetParameter("title", title);
		nh.addGetParameter("writer", writer);
		nh.addGetParameter("myImagePath", myImageName);
		nh.addGetParameter("bikeImagePath", bikeImageName);
		nh.addGetParameter("bikeType", bikeType);
		nh.addGetParameter("tradeType", tradeType);
		nh.addGetParameter("shareType", shareType);
		nh.addGetParameter("lati", lati);
		nh.addGetParameter("longi", longi);
		nh.addGetParameter("costPerTime", "");
		nh.addGetParameter("costPerDay", "");
		nh.addGetParameter("costPerWeek", "");
		nh.addGetParameter("content", content);
		nh.executeHttpMethod();
	}

	private void uploadMyBikePic(WorkVectors wv) {
		Log.e(TAG, "uploadmypic");
		String strPath = wv.getData(WorkVectors.BIKE_IMG_PATH) + "";
		Log.e(TAG + " uploadMypic", strPath);
		// nh.doFileUpload(strPath, Variable.SERVER_IMG_URL);
		nh.doFileUpload(strPath, Variable.SERVER_IMG_URL);

	}

	// http://125.209.193.11:8080/etbike/shareBoard/getMyBikeList/doo.kim.54/

	private void uploadMyProfilePic(WorkVectors wv) {
		String strPath = wv.getData(WorkVectors.MY_PROFLE_IMG_PATH) + "";
		nh.doFileUpload(strPath, Variable.SERVER_IMG_URL);
	}

	private void uploadMyBikePicAtFeed(WorkVectors wv) {
		Log.e(TAG, "uploadpic at facebook");
		fhm = FHaloManager.getInstance();
		String strImgPath = wv.getData(WorkVectors.BIKE_IMG_PATH) + "";
		String detail = wv.getData(WorkVectors.BIKE_INFO_CONTENT) + "";
		String fileName = nh.getFileName(strImgPath);
		// String
		fhm.updatePhote(detail, strImgPath, fileName);

	}
	/*
	 * 
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class UploadToServer {

	public void doFileUpload(String path, String serverUrl) {
		try {
			
			HttpClient httpClient = new DefaultHttpClient();
			String url = serverUrl;
			HttpPost post = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("title", "송태웅"));
			nameValuePairs.add(new BasicNameValuePair("content", "송태웅"));
			nameValuePairs.add(new BasicNameValuePair("writer", "송태웅"));
			UrlEncodedFormEntity entityRequest =  new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			post.setEntity(entityRequest);
			HttpResponse response = httpClient.execute(post);
			HttpEntity resEntity = response.getEntity();
			InputStream stream = resEntity.getContent();
			Scanner sc = new Scanner(stream);
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

	 */

}
