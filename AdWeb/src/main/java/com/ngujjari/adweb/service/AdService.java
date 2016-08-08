package com.ngujjari.adweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ngujjari.adweb.model.Ad;

@Service
@CacheConfig(cacheNames = "adcache")
public class AdService {

	private ConcurrentHashMap<String, Ad> adMessages
	   = new ConcurrentHashMap<String, Ad>();
	
	@Cacheable
	public Ad getByPartner_id(String partner_id) {
		
		return adMessages.get(partner_id);
	}

	@CachePut
	public Ad updateAd(Ad uAd) {
		// TODO Auto-generated method stub
		adMessages.put(uAd.getPartner_id(), uAd);
		return uAd;
	}

	@CacheEvict
	public void deleteAd(Ad delAd)
	{
		adMessages.remove(delAd);
	}
	
	public List<Ad> getAll()
	{
		return new ArrayList<Ad>(adMessages.values());
	}
	
}
