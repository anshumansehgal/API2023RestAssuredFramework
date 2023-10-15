package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.StringUtils;

import io.restassured.module.jsv.JsonSchemaValidator;

public class APISchemaValidationTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop, baseURI);
	}
	
	//Schema Validation
		@Test
		public void createUserAPISchemaTest() {
			
			//1. POST
			//non builder approach
			User user = new User("Tom", StringUtils.getRandomEmailId(), "male", "active");
			
			restClient.post(GOREST_ENDPOINT, "json", user, true, false) //for requestBody--Use POJO(src/main/java)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
							 .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createuserschema.json"));
			
			
			
		}

}
