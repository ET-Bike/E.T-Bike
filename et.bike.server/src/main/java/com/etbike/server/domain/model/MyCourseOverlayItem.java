package com.etbike.server.domain.model;

import javax.persistence.Entity;


import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class MyCourseOverlayItem extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8061940072661739579L;


	/*
	 * title
	 */	
	private String sLoc;
	private String eLoc;
    
    private Double sLatitude;
    private Double sLongitude;
    
    private Double eLatitude;
    private Double eLongitude;
    
    
	private String time;
	private String detail;
    private String title;

	public MyCourseOverlayItem(String sLoc, String eLoc, String title, String time, String detail) {
		// TODO Auto-generated constructor stub
		this.sLoc = sLoc;
		this.eLoc = eLoc;
		this.time = time;
		this.detail = detail;

	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getsLoc() {
		return sLoc;
	}

	public void setsLoc(String sLoc) {
		this.sLoc = sLoc;
	}

	public String geteLoc() {
		return eLoc;
	}

	public void seteLoc(String eLoc) {
		this.eLoc = eLoc;
	}


	public String getDetail() {
		return detail;
	}

	public double getsLatitude() {
		return sLatitude;
	}

	public void setsLatitude(double sLatitude) {
		this.sLatitude = sLatitude;
	}

	public double getsLongitude() {
		return sLongitude;
	}

	public void setsLongitude(double sLongitude) {
		this.sLongitude = sLongitude;
	}

	public double geteLatitude() {
		return eLatitude;
	}

	public void seteLatitude(double eLatitude) {
		this.eLatitude = eLatitude;
	}

	public double geteLongitude() {
		return eLongitude;
	}

	public void seteLongitude(double eLongitude) {
		this.eLongitude = eLongitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


}






