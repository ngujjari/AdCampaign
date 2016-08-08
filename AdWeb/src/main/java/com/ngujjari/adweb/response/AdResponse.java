package com.ngujjari.adweb.response;

import java.util.List;

import com.ngujjari.adweb.model.Ad;
import com.ngujjari.adweb.util.AdError;

public class AdResponse {

	Ad ad;
	List<Ad> adList;
	AdError error;
	
	public Ad getAd() {
		return ad;
	}
	public void setAd(Ad ad) {
		this.ad = ad;
	}
	public List<Ad> getAdList() {
		return adList;
	}
	public void setAdList(List<Ad> adList) {
		this.adList = adList;
	}
	public AdError getError() {
		return error;
	}
	public void setError(AdError error) {
		this.error = error;
	}
}
