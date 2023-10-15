package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

//For different API
//Cannot run directly from here. Why? Because otherwise how exactly this test case got to know that we've to supply the parameter.  

public class CircuitTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop, baseURI);
	}
	
	//Get country value
	@Test
	public void getCircuitTest() {
		
		//1.Get the response
		Response circuitResponse = restClient.get(CIRCUIT_ENDPOINT + "/2017/circuits.json", false, false);
			
		//2. Do the method chaining for other stuff
		circuitResponse
				.then()
					.assertThat()
						.statusCode(APIHttpStatus.OK_200.getCode());	
								
	//we can skip writing below 2 lines	as we covered this in method chaining
	//	int statusCode = circuitResponse.statusCode();
	//	Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());
		
	//3. Use JsonPathValidator
	JsonPathValidator js = new JsonPathValidator();
	List<String> countryList =  js.readList(circuitResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
	System.out.println(countryList); //[China]
	Assert.assertTrue(countryList.contains("China"));
	
	//Assertion is imp. Keep the JsonPathValidator handy if you want to use it. We can use above approach and below chaining concept also.
	//Not mandatory to use JsonPathValidator. Can update other test cases like GetUserTest like above 
	
				//	.then().log().all()
				//		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
				
	}
	
	
	
	

}
