package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

import io.restassured.module.jsv.JsonSchemaValidator;

import static org.hamcrest.Matchers.*;


public class CreateUserTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@DataProvider
	public Object[][] createUserTestData() {
		
		return new Object[][] {
			{"Anshu", "male", "active"},
			{"Neha", "female", "inactive"},
			{"Rob", "male", "inactive"}
		};
	}
	
	//Excel - data provider
	@DataProvider
	public Object[][] createUserExcelTestData() {
		
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
	}
	
	
	@Test(dataProvider = "createUserExcelTestData")
	public void createUserTest(String name, String gender, String status) {
		
		//1. POST
		//non builder approach
		User user = new User(name, StringUtils.getRandomEmailId(), gender, status);
		
		Integer userId = restClient.post(GOREST_ENDPOINT, "json", user, true, false) //for requestBody--Use POJO(src/main/java)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
							.extract().path("id");
		
		System.out.println("user id: " + userId);
					
		//2. GET
		RestClient clientGet = new RestClient(prop, baseURI); //create a new restClient obj because Post one is different from Get call. 
		clientGet.get(GOREST_ENDPOINT + "/" + userId, true, true)
			.then().log().all()
				.assertThat()
					.statusCode(APIHttpStatus.OK_200.getCode())
						.and()
							.body("id", equalTo(userId));
		
		
	}
	
	
	

}
