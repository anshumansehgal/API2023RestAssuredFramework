package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.qameta.allure.Description;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest{ //IS A child of BaseTest
	
	//create a obj. of restClient for every method | otherwise it will keep appending with other obj. properties
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Description("This description is coming from allure reports.")
	@Test(enabled = false, description = "This is coming from testNG.")
	public void getAllUsersTest() {
		
		//Having same serviceURL hardcoded value is called as "code smell" SonarQube
		restClient.get(GOREST_ENDPOINT, true, true)
					.then().log().all()  //Assertion should be in Test(testNG) class (here) | We're using same restClient so there's a bug in the code #resolve it 
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(enabled = false)
	public void getTheUserTest() {
		
		restClient.get(GOREST_ENDPOINT + "/" + "5298062", true, false)  //5272780--random user
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
							.and()
								.body("id", equalTo(5298062));  //add matcher with import static
	}
	
	//query parameters/ 2 query parameters
	@Test
	public void getUserWithQueryParamsTest() {
		
		//create hashmap for query params-Why HashMap?Because Query params are always in the form key-value pair
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "Ashuu");
		queryParams.put("status", "active");
		
		
		restClient.get(GOREST_ENDPOINT,null, queryParams, true, true) //no header so we can put null
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode()); 
	}
	

}
