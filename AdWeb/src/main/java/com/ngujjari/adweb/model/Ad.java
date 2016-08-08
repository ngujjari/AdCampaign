package com.ngujjari.adweb.model;

import java.util.Date;

public class Ad {

	String partner_id;
	Integer duration;
	String ad_content;
	
	Date timeActive;
	
	public Ad(){}
	public Ad(String partner_id, Integer duration, String ad_content)
	{
		this.partner_id = partner_id;
		this. duration = duration;
		this. ad_content = ad_content;
		this.timeActive = new Date();
	}
	
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getAd_content() {
		return ad_content;
	}
	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}
	public Date getTimeActive() {
		return timeActive;
	}
	public void setTimeActive(Date timeActive) {
		this.timeActive = timeActive;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Ad a = (Ad) o;

		return this.partner_id.equals(a.partner_id);
	}

	@Override
	public int hashCode() {
		return this.partner_id.hashCode();
	}
	
	
}
