package com.etbike.server.support.img;

public interface ThumbnailCreatable {
	
	void create(String sourceImagePath, String thumbnailImagePath, int width, int height);
	
}
