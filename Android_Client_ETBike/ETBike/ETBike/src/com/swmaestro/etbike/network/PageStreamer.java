package com.swmaestro.etbike.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.util.Log;

public class PageStreamer {

	int size;
	Argv argv[];
	
	Argv postArgv[];
	int postSize;
	HttpURLConnection con;
	URL url;
	
	String sURL;
	String servName;
	String TAG = "PageStremer";
	String method;
	
	
	
	public static final String METHOD_GET = "get";
	public static final String METHOD_PUT = "put";
	public static final String METHOD_POST = "post";
	
	
	public void clear() {
		size = 0;
		argv = new Argv[1024];
		postArgv = new Argv[1024];
		sURL = "";
		servName = "";
		method = METHOD_GET;
		
		
	}

	public PageStreamer() {
		// TODO Auto-generated constructor stub
		argv = new Argv[1024];
		size = 0;
		
		postArgv = new Argv[1024];
		postSize = 0;
	}
	
	public void setMethodType(String method) {
		this.method = method;
	}
	
	public void setServURL(String servName) {
		this.servName = servName;
	}

	public void add(String key, String value) {

		// String value1 = URLEncoder(new String(value.getBytes("UTF-8")));

		try {
			argv[size++] = new Argv(key, java.net.URLEncoder.encode(new String(value.getBytes("UTF-8"))));
			
//			argv[size++] = new Argv(key, value);
			
			postArgv[postSize++] = new Argv(key, value);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}



	/*
	 * String url = Var.SERVER_URL + Var.SERVER_USER_FOLDER + "?" + "uid=" + uid
	 * + "&" + "pwd=" + pwd;
	 */
	public String getArgv() {
		String result = "";
		for (int i = 0; i < size; i++) {
			// 처占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占싸곤옙占�
			if (i == size - 1 && i == 0) {
				result += "?" + argv[i].getKey() + "=" + argv[i].getValue();
			}
			// 처占쏙옙占쏙옙 占쏙옙占�
			else if (i == 0) {
				result += "?" + argv[i].getKey() + "=" + argv[i].getValue()
						+ "&";
			}
			// 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占�
			else if (i == size - 1) {
				result += argv[i].getKey() + "=" + argv[i].getValue();
			}

			else {
				result += argv[i].getKey() + "=" + argv[i].getValue() + "&";
			}
		}
		return result;
	}

	public String executeGet() {

		String url = servName + getArgv();

		Log.e("TAG", url);

		StringBuffer sb = new StringBuffer(1024);

		java.io.BufferedReader in = null;

		try {
			String line = "";
			java.net.URL ocu = new java.net.URL(url);
			java.net.URLConnection con = ocu.openConnection();
			in = new java.io.BufferedReader(new java.io.InputStreamReader(
					con.getInputStream(), "UTF-8"));

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("pageSteam in getPageSteram", sb.toString());
		return sb.toString();
	}
	
	public String executeMethod() {
		
		String sUrl = servName + getArgv();
		Log.e(TAG + " executeMethod", "url = " +  sUrl);
		
		
		try {

			 StringBuffer sb= new StringBuffer();
			  HttpClient http = new DefaultHttpClient();
			  HttpResponse response = null;
			  if(method.equals(METHOD_GET)) {
				  response = http.execute(new HttpGet(sUrl));  
			  } 

			  else if(method.equals(METHOD_PUT)){
				  Log.e(TAG + " executeMEthod", "method == put");
				  HttpPut hp = new HttpPut(sUrl);
				  hp.addHeader("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3");
				  hp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				  response = http.execute(hp);  
				  
				  
			  }
			  
			  else if(method.equals(METHOD_POST)) {
				  
				  HttpClient httpClient = new DefaultHttpClient();
					HttpPost post = new HttpPost(servName);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);					
					for(int i = 0; i < size; i++) {
						nameValuePairs.add(new BasicNameValuePair(postArgv[i].getKey(), postArgv[i].getValue()));	
						Log.e(TAG + " post method", "post value(key, value)" + postArgv[i].getKey() + " " +postArgv[i].getValue());
					}
					UrlEncodedFormEntity entityRequest =  new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
					post.setEntity(entityRequest);
					response = httpClient.execute(post);
				  
			  }
			    HttpEntity resEntity = response.getEntity();
				InputStream stream = resEntity.getContent();
				Scanner sc = new Scanner(stream);

				while (sc.hasNextLine()) {
//					System.out.println(sc.nextLine());
					sb.append(sc.nextLine());
				}
				Log.e(TAG +" executeMethod", "result = " +sb.toString());
				return sb.toString();
			
			    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

	
	

}



class Argv {

	private String key;
	private String value;

	public Argv(String key, String value) {

		this.key = key;
		this.value = value;

	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
