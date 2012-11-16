package com.swmaestro.etbike.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Entity;
import android.util.Log;

import com.swmaestro.variable.Variable;

public class NetworkHelper {
	String TAG = "NetworkHelper";
	PageStreamer ps;
	
	

	public NetworkHelper() {
		// TODO Auto-generated constructor stub
		ps = new PageStreamer();
	}

	public boolean downloadImage(String downloadUrl, String path) {

		int read;
		String mTAG = " downloadImage";

		String sFolderPath = path.substring(0, path.lastIndexOf("/"));
		Log.e(TAG + mTAG, sFolderPath);
		makeDir(sFolderPath);

		File fPath = new File(path);
		//http://125.209.193.11:8080/etbike/thumb/7/50
		//http://125.209.193.11:8080/etbike/thumb/7


		if (fPath.exists())
			return true;

		try {
			URL url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			int len = conn.getContentLength();
			byte[] bt = new byte[len];
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(fPath);

			while (true) {
				read = is.read(bt);
				if (read <= 0)
					break;
				fos.write(bt, 0, read);
			}

			is.close();
			fos.close();
			conn.disconnect();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}

		return true;
	}

	public void makeDir(String folderPath) {
		File sFolderFile = new File(folderPath);
		sFolderFile.mkdir();
	}

	public String getFileName(String path) {
		int index = path.lastIndexOf('/');
		int lastIndexOfFile = path.length()-1;
		Log.e(TAG + " getFileName", "index = " + index);
		Log.e(TAG + " getFileName", "lastindex = " + lastIndexOfFile);
		if(index == lastIndexOfFile) {
			path = path.substring(0,lastIndexOfFile);
			Log.e(TAG + " getFileName", "path = " + path);
			index = path.lastIndexOf('/'); 
		}
		String fileName = path.substring(index + 1);
		Log.e(TAG + " getFileName", "fileName = " + fileName);
		return fileName;
		
//		http://125.209.193.11:8080/etbike/img/7


	}
	
	protected String getThumbNailFileName(String path){
		
		int lastIndexOfFile = path.length();
//		return path.substring(lastIndexOfFile-6, )
		String thumbNailPath = path.substring(0, lastIndexOfFile -3);
		Log.e(TAG + "getThumbNailFileName" ,"thumbpath = " + thumbNailPath);
		return getFileName(thumbNailPath);
		
		
//		String path = path.substring(0,lastIndexOfFile);
//		while(true) {
//			Log.e(TAG + "getThumbNailFileName" ,"in for loop");
//			if(path.charAt(i) == '/') {
//				j++;
//			}
//			if(j == 2) {
//				Log.e(TAG + "getThumbNailFileName" ,"path's substring = " + path.substring(0, j));
//				return getFileName(path.substring(0, j));
//			}
//			if(i==0) {
//				Log.e(TAG + "getThumbNailFileName" ,"name not found");
//				return null;
////				return  new IOException ;
//			}
//			i--;
//		}
		
		
	}

	public File getStrPathByFile(String downloadUrl) {
		String fileName = getFileName(downloadUrl);
		String strPath = Variable.ROOT_DIR + fileName;
		return new File(strPath);
	}

	public String getStrPathByString(String downloadUrl) {
		String fileName = getFileName(downloadUrl);
		String strPath = Variable.ROOT_DIR + fileName;
		return strPath;
	}

	public void setGetServUrl(String rootURL) {
		ps.setServURL(rootURL);
	}

	public void addGetParameter(String key, String value) {
		ps.add(key, value);
	}

	public String executeGet() {
		return ps.executeGet();
	}
	
	public String executeHttpMethod() {
		return ps.executeMethod();
	}
	
	public void setMethodType(String method) {
		ps.setMethodType(method);
	}

	public void doFileUpload(String strPath, String serverUrl) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// String url = "http://172.20.11.235:8080/web1/file.jsp";
			String url = serverUrl;
			HttpPost post = new HttpPost(url);
			File saveFile = new File(strPath);
			if (saveFile.exists())
				System.out.println("있");

			FileBody bin = new FileBody(saveFile);
			
			MultipartEntity multipart = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			multipart.addPart("upload", bin);
			
			post.setEntity(multipart);
			
			
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
	
	public void PUT(String strPath, String serverUrl) {
		try {
			 URL u = new URL("https://api.bitbucket.org/1.0/repositories/myname/myproject/issues/1/");
			  HttpClient http = new DefaultHttpClient();
			    HttpPut putmethod = new HttpPut("http://125.209.193.11:8080/etbike/boards");

			    putmethod.addHeader("title", "조영국");
			    putmethod.addHeader("writer", "방구");
			    putmethod.addHeader("content", "영궈");
			    putmethod.addHeader("categoryValue", "home");
			    HttpResponse response = http.execute(putmethod);
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
	
	
	public void clearGetAndPUT() {
		ps.clear();
	}
	
	

	
	

}
