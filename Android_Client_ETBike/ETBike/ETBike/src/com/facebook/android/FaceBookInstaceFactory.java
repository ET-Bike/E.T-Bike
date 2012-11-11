package com.facebook.android;

import com.swmaestro.variable.Variable;

public class FaceBookInstaceFactory {
	public static final String STATE_NULL = "STATE_NULL";
	public static final String STATE_NORMAL = "STATE_NORMAL";
	
	private static String state = null;
	
	private static Facebook mFacebook;
	
	public static void setFaceBook(Facebook fb) {
		state = STATE_NORMAL;
		mFacebook = fb;
	}
	
	public static Facebook getInstance() {
		NullPointerException npe = null;
		if(mFacebook == null) {
			mFacebook = new Facebook(Variable.FACEBOOK_APP_ID);
			
		}
		return mFacebook;
		
	}
	
	
	

}

