package com.ngujjari.adweb;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.*;

import com.ngujjari.adweb.model.Ad;
import com.ngujjari.adweb.response.AdResponse;
import com.ngujjari.adweb.service.AdService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class AdWebApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private AdService adService;

	@Before
	public void setup() {
		
	}

	@Test
	public void contextLoads() {
	}



	@Test
	public void postAdTest() {
		ResponseEntity<AdResponse> responseEntity =
				restTemplate.postForEntity("/addemo/postad", new Ad("123",10,"This is a TEST Ad !!"), AdResponse.class);
		AdResponse adRes = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("123", adRes.getAd().getPartner_id().toString());

	}

	@Test
	public void readAdTest() {

		given(adService.getByPartner_id("123")).willReturn(new Ad("123",100,"This is a TEST Ad !!"));
		
		ResponseEntity<AdResponse> responseEntity =
				restTemplate.getForEntity("/addemo/cache/ad/123", AdResponse.class);
		AdResponse adRes = responseEntity.getBody();

		
		assertEquals("123", adRes.getAd().getPartner_id());
		assertEquals("100", adRes.getAd().getDuration().toString());

	}

}