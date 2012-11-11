package com.swmaestro.etbike.activity;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.android.DialogError;
import com.facebook.android.FaceBookInstaceFactory;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.halo.application.types.User;
import com.facebook.halo.framework.common.AccessToken;

@SuppressLint("NewApi")
public class LoginActivity extends Activity {
	
	Context context;
	public static final String FACEBOOK_APP_ID = "354574991265954";
	Facebook mFaceBook;
	String TAG = "MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //loading
        startActivity(new Intent(context, LoadingActivity.class));
        setContentView(R.layout.activity_main);
        
        mFaceBook = new Facebook(FACEBOOK_APP_ID);
        
        AccessToken.setAccessToken(mFaceBook.getAccessToken());
//        
//        User user = new User();
//        
//        user = user.createInstance("me");
//        System.out.println(user.getPicture());
//        
        
        /*
         * init values
         */
        
        
        findViewById(R.id.loginBtnMain).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
				
			}
		});
        
        
        
        
        
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
	  {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    if (resultCode == RESULT_OK)
	    {
	      if (requestCode == 32665)
	      {
	        mFaceBook.authorizeCallback(requestCode, resultCode, data);
	        Log.e(TAG + "onActivity result", "facebook result ok");
	        Log.e(TAG + "onActivity result", "accesstoken" + mFaceBook.getAccessToken());
	      }
	    }
	    else
	    {
	      if (requestCode == 32665)
	      {
	        mFaceBook.authorizeCallback(requestCode, resultCode, data);
	      }      
	    }
	  }
	
    
    
    private void login()  {
    	
    	AuthorizeListener al = new AuthorizeListener(context, mFaceBook);
    	
    	mFaceBook.authorize2(this, new String[] {"publish_stream, user_photos, email"}, al);
    	
    }

 

    
}


class AuthorizeListener implements DialogListener {
	String TAG = "AuthorizeListener";
	Context context;
	Facebook mFacebook;
	
	
	public AuthorizeListener(Context context, Facebook mFacebook) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mFacebook = mFacebook;
	}
	
	
	@Override
	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		Log.e(TAG  , "onCompelte start");
		Log.e(TAG  , "facebook token" + mFacebook.getAccessToken());
		
		FaceBookInstaceFactory.setFaceBook(mFacebook);
		
		context.startActivity(new Intent(context, MainActivity.class));
		
		
		
	}

	@Override
	public void onFacebookError(FacebookError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(DialogError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		
	}
	//11-10 18:10:51.600: E/NetworkManager(18305): downlaod image url = https://graph.facebook.com/100001290958599/picture

	 
	
}



