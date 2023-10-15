package com.qa.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class AmadeusAPITest extends BaseTest {
	
	private String accessToken;

	@Parameters({"baseURI", "grantType", "clientId", "clientSecret"})
	@BeforeMethod
	public void flightAPISetUp(String baseURI, String grantType, String clientId, String clientSecret) {
		restClient = new RestClient(prop, baseURI);
		
		//Call the method created from RestClient
		accessToken = restClient.getAccessToken(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
	}
	
	//creating obj. of RestClient once again? How many APIs we're calling here? 2 APIs--one for accessToken and one for flight booking. Also help in passing baseURI 
	
	@Test
	public void getFlightInfoTest() {
		
		RestClient restClientFlight = new RestClient(prop, baseURI);
		
		//headersMap <String, String>
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Authorization", "Bearer " + accessToken);
		
		//queryParams <String, Object>
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", 200);
		
		
		String type = restClientFlight.get(AMADEUS_FLIGHTBOOKING_ENDPOINT, queryParams, headersMap, false, true)
							.then().log().all()
								.assertThat()
									.statusCode(APIHttpStatus.OK_200.getCode())
										.and()
											.extract()
												.response()
													.jsonPath()
														.get("data[0].type");
		
		System.out.println(type); //flight-destination
		
		
		
	}
	
	
}
