package com.etbike.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etbike.server.domain.model.MyCourseOverlayItem;
import com.etbike.server.persistence.MyCourseOverlayItemRepository;
import com.etbike.server.persistence.MyCourseOverlayItemSpecifications;
import com.etbike.server.service.MyCourseOverlayItemService;

@Service
public abstract class MyCourseOverlayItemServiceImpl implements MyCourseOverlayItemService{

	@Autowired private MyCourseOverlayItemRepository myCourseRepository;
	
	@Override
	public MyCourseOverlayItem saveMyCourse(MyCourseOverlayItem myCourseOverlayItem) {
		// TODO Auto-generated method stub
		return myCourseRepository.save(myCourseOverlayItem);
	}

	public MyCourseOverlayItem findByCoordinates(double sLatitude,double sLongitude) {
		// TODO Auto-generated method stub
		return myCourseRepository.findOne(MyCourseOverlayItemSpecifications.isCoordinates(sLatitude, sLongitude));
		
	}
}
