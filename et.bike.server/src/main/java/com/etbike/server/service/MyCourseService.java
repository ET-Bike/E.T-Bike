package com.etbike.server.service;

import com.etbike.server.domain.model.MyCourseOverlayItem;

public interface MyCourseService {

	MyCourseOverlayItem saveMyCourse(MyCourseOverlayItem myCourseOverlayItem);
	MyCourseOverlayItem findByCoordinates(double sLatitude, double sLongitude);
}
