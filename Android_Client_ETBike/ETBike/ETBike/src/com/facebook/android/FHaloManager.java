package com.facebook.android;

import com.facebook.halo.application.types.User;
import com.facebook.halo.application.types.connection.Photos;
import com.facebook.halo.framework.common.AccessToken;

public class FHaloManager {

	Facebook mFacebook;
	User user;
	Photos photo;

	static FHaloManager fhm;

	private FHaloManager() {
		// TODO Auto-generated constructor stub
		this.mFacebook = FaceBookInstaceFactory.getInstance();
		AccessToken.setAccessToken(mFacebook.getAccessToken());
		user = new User();
		user = user.createInstance("me");
		photo = new Photos();
	}

	public static FHaloManager getInstance() {
		if (fhm == null) {
			fhm = new FHaloManager();
		}
		return fhm;
	}
	

	public String getMyProfilePic() {

		return user.getPicture();
	}

	public String getMyLastName() {

		return user.getLastName();

	}

	public String getMyFirstName() {

		return user.getFirstName();

	}

	public String getUserName() {

		return user.getUsername();

	}

	public boolean updatePhote(String detail, String strImgPath, String fileName) {

		photo.setMessage(detail);
		photo.setSource(strImgPath);
		photo.setFileName(fileName);

		user.publishPhotos(user.albums().getData().get(0).getId(), photo);
		return true;
	}

	// public String get() {
	// return user.getp
	// }

	public boolean publishFeed() {
		return true;
	}

}
