package com.ngujjari.adweb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ngujjari.adweb.model.Ad;
import com.ngujjari.adweb.response.AdResponse;
import com.ngujjari.adweb.util.AdError;


@RestController
@RequestMapping("/addemo")
public class AdWebController {

   
    static Map<String, Ad> adData = new HashMap<String, Ad>();
    
    @RequestMapping(method = RequestMethod.POST, value = "/postad")
	@ResponseBody
	public ResponseEntity<AdResponse> postAdContent(
			@RequestBody Ad adReq) {

    	String partnerId = adReq.getPartner_id();
		System.out.println(" partner ID: "+adReq.getPartner_id());
    	AdResponse response = new AdResponse();
		AdError error = new AdError();
		error.setErrorCd(200);
		error.setUserMsg("Ad campaign Saved Successfully !!"); // init message
		
		if(adData.containsKey(partnerId)){
			Ad prevAd = adData.get(partnerId);
			Date prevTime = prevAd.getTimeActive();
			Calendar cal = Calendar.getInstance();
			cal.setTime(prevTime);
			cal.add(Calendar.SECOND, prevAd.getDuration());
			
			if(cal.getTime().after(new Date())){ // ad time still active. no duplicate adds.
				error.setErrorCd(100);
				error.setUserMsg("Your previous add campaign is in active status. Wait until it completes...");
				response.setError(error);
			}
			else{
				adReq.setTimeActive(new Date());
				response.setAd(adReq);
				adData.put(partnerId, adReq);
			}
		}
		else{
    	
	    	adReq.setTimeActive(new Date());
	    	response.setAd(adReq);
	        adData.put(adReq.getPartner_id(), adReq);
		}
    	
    	response.setError(error);
		return new ResponseEntity<AdResponse>(response,  new HttpHeaders(),
				HttpStatus.OK);

	}
    
    @RequestMapping(value="/ad/{partnerId}", method=RequestMethod.GET) 
    public ResponseEntity<AdResponse> showAd(@PathVariable String partnerId) {
    	
    	System.out.println("partnerId == "+partnerId);
    	AdResponse response = new AdResponse();
    	AdError error = new AdError();
    	error.setErrorCd(200);
    	if(adData.containsKey(partnerId)){
    		Ad prevAd = adData.get(partnerId);
    		Date prevTime = prevAd.getTimeActive();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(prevTime);
    		cal.add(Calendar.SECOND, prevAd.getDuration());
    		
    		if(cal.getTime().before(new Date())){
    			error.setUserMsg("No active ad campaigns exist for the pertner "+ partnerId);
    			error.setErrorCd(100);
    			response.setError(error);
    		}
    		else
    		{
    			response.setAd(adData.get(partnerId));
    		}
    	}
    	else{
    		error.setUserMsg("No ad campaigns exist for the pertner "+ partnerId);
    		error.setErrorCd(100);
			response.setError(error);
    	}
    	return new ResponseEntity<AdResponse>(response,  new HttpHeaders(),
				HttpStatus.OK);
    }
    
    @RequestMapping(value="/ad", method=RequestMethod.GET) 
    public ResponseEntity<AdResponse> showAll() {
    	
    	AdResponse response = new AdResponse();
    	
    	Collection<Ad>  list = adData.values();
    	response.setAdList(new ArrayList<Ad>(list));
    	
    	return new ResponseEntity<AdResponse>(response,  new HttpHeaders(),
				HttpStatus.OK);
    }
    
    
    
}
