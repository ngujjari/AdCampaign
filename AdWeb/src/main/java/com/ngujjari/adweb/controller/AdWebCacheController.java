package com.ngujjari.adweb.controller;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ngujjari.adweb.service.AdService;
import com.ngujjari.adweb.util.AdError;


@RestController
@RequestMapping("/addemo/cache")
public class AdWebCacheController {

	@Autowired
	AdService adService;
   
    // static Map<String, Ad> adData = new HashMap<String, Ad>();
    
    @RequestMapping(method = RequestMethod.POST, value = "/postad")
	@ResponseBody
	public ResponseEntity<AdResponse> postAdContent(
			@RequestBody Ad adReq) {

    	String partnerId = adReq.getPartner_id();
		System.out.println(" partner ID: "+adReq.getPartner_id());
    	AdResponse response = new AdResponse();
		AdError error = new AdError();
		error.setUserMsg("Ad campaign Saved Successfully !!"); // init message
		error.setErrorCd(200);
		Ad refAd =  adService.getByPartner_id(partnerId);
		if(refAd != null){
			
			Date prevTime = refAd.getTimeActive();
			Calendar cal = Calendar.getInstance();
			cal.setTime(prevTime);
			cal.add(Calendar.SECOND, refAd.getDuration());
			
			if(cal.getTime().after(new Date())){ // ad time still active. no duplicate adds.
				error.setErrorCd(100);
				error.setUserMsg("Your previous add campaign is in active status. Wait until it completes...");
				response.setError(error);
			}
			else{
				adReq.setTimeActive(new Date());
				response.setAd(adReq);
				adService.updateAd(adReq);
			}
		}
		else{
    	
	    	adReq.setTimeActive(new Date());
	    	response.setAd(adReq);
	    	adService.updateAd(adReq);
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
    	Ad refAd =  adService.getByPartner_id(partnerId);
		if(refAd != null){
    		//Ad prevAd = adData.get(partnerId);
    		Date prevTime = refAd.getTimeActive();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(prevTime);
    		cal.add(Calendar.SECOND, refAd.getDuration());
    		
    		if(cal.getTime().before(new Date())){
    			error.setErrorCd(100);
    			error.setUserMsg("No active ad campaigns exist for the pertner "+ partnerId);
    			response.setError(error);
    		}
    		else
    		{
    			response.setAd(adService.getByPartner_id(partnerId));
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
    	
    	response.setAdList(adService.getAll());
    	
    	return new ResponseEntity<AdResponse>(response,  new HttpHeaders(),
				HttpStatus.OK);
    }
    
    
    
}
