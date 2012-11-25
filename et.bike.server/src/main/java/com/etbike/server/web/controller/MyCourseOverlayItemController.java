package com.etbike.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.etbike.server.domain.model.MyCourseOverlayItem;
import com.etbike.server.service.MyCourseOverlayItemService;

@Controller
public class MyCourseOverlayItemController {

	 @Autowired
	 private MyCourseOverlayItemService myCourseService;
	 
		@RequestMapping(value = "/myCourse", method = RequestMethod.PUT)
		public String add(MyCourseOverlayItem model, ModelMap map) {
			MyCourseOverlayItem myCourseOverlayItem = myCourseService.saveMyCourse(model);
			map.put("myCourseOverlayItem", myCourseOverlayItem);

			return "jsonView";
		}
		
		@RequestMapping(value = "/getMyCourse")
		public String getMyCourse(@PathVariable double sLatitude, double sLongitude, ModelMap map) {
			MyCourseOverlayItem myCourseOverlayItem = myCourseService.findByCoordinates(sLatitude,sLongitude);
			map.put("myCourseOverlayItem", myCourseOverlayItem);
			return "jsonView";
		}

	
}
