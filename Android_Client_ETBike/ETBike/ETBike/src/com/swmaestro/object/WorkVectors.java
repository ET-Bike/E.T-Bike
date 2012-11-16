package com.swmaestro.object;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class WorkVectors {
	public class Entry {
		String key;
		String type;
		Object data;

		public Entry(String key, String type, Object data) {
			this.key = key;
			this.type = type;
			this.data = data;
		}
	}

	// public static String KEY_TIME;

	private HashMap<String, Entry> map = new HashMap<String, Entry>();

	public static final String KEY_URL_LIST = "urlList";
	public static final String DIAL_TYPE = "dial_type";
	public static final String DIAL_TYPE_BIKE = "dial_type_bike";
	public static final String DIAL_TYPE_TRADE = "dial_type_trade";
	public static final String DIAL_TYPE_SHARE = "dial_type_share";

	public static final String BIKE_TYPE = "my_bike_bike_type";
	public static final String BIKE_TYPE_MOUNTAIN = "산악용";
	public static final String BIKE_TYPE_COMMUTE = "출퇴근용";
	public static final String BIKE_TYPE_PLAYER = "선수용";

	public static final String TRADE_TYPE = "my_bike_trade_type";
	public static final String TRADE_TYPE_DELIEVERY = "택배";
	public static final String TRADE_TYPE_DIRECT_DEAL = "직거래";

	public static final String SHARE_TYPE = "my_bike_share_type";
	
	public static final String SHARE_TYPE_RENT = "대여";
	public static final String SHARE_TYPE_DONATION = "기부";
	public static final String SHARE_TYPE_SELL = "판매";

	public static final String DIAL_CONTENT = "dial_content";
	
	public static final String BIKE_LOCATION = "search_location";
	public static final String BIKE_LATITUDE = "search_lat";
	public static final String BIKE_LONGITUDE = "search_lon";
	
	public static final String COST_TIME = "cost_time";
	public static final String COST_DAY = "cost_day";
	public static final String COST_WEEK = "cost_week";
	
	public static final String MDLA_BIKE_VIEW_TYPE = "mdla_bike_view";
	
	public static final String MY_PROFLE_IMG_PATH = "profile_img";
	
	public static final String BIKE_IMG_PATH = "bike_img_path";
	
	public static final String BIKE_INFO_CONTENT = "bike_info_detail";
	
	public static final String UPLOAD_FACEBOOK_FLAG = "upload_facebook_flag";
	
	public static final String MY_BIKE_LIST = "my_bike_list";
	
	public static final String SELECTED_MY_BIKE = "my_bike";
	
	public static final String SHARE_BIKE = "share_bike";
	
	public static final String FILTER_TYPE ="filter_type";
	
	
	

	public synchronized void put(String key, String type, Object data) {
		map.put(key, new Entry(key, type, data));
	}

	public synchronized void put(Entry entry) {
		map.put(entry.key, entry);
	}

	public synchronized void put(String key, String value) {
		map.put(key, new Entry(key, "string", value));
	}

	public synchronized void put(String key, int value) {
		map.put(key, new Entry(key, "int", value));
	}

	public synchronized void put(String key, double value) {
		map.put(key, new Entry(key, "double", value));
	}

	public synchronized void put(String key, boolean value) {
		map.put(key, new Entry(key, "boolean", value));
	}

	public synchronized String getType(String key) {
		return map.get(key).type;
	}

	public synchronized Object getData(String key) {
		return map.get(key).data;
	}

	public synchronized Entry get(String key) {
		return map.get(key);
	}

	public synchronized Set<String> keySet() {
		return map.keySet();
	}

	public synchronized boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public synchronized Collection<Entry> values() {
		return map.values();
	}

	public synchronized int size() {
		return map.size();
	}
}
