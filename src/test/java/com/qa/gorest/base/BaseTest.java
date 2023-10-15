package com.qa.gorest.base;

import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

//parent class of all the test classes
public class BaseTest {
	
	//Service URLs
	public static final String GOREST_ENDPOINT = "/public/v2/users";
	public static final String REQRES_ENDPOINT = "/api/users";
	public static final String CIRCUIT_ENDPOINT = "/api/f1";
	public static final String AMADEUS_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
	public static final String AMADEUS_FLIGHTBOOKING_ENDPOINT = "/v1/shopping/flight-destinations";
	
	
	protected ConfigurationManager config; //to call initProp() method
	protected Properties prop;
	protected RestClient restClient;
	protected String baseURI;
	
	//setup method
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseURI) {
		
		//Allure Report
		RestAssured.filters(new AllureRestAssured());
		
		config = new ConfigurationManager();
		prop = config.initProp();
		this.baseURI = baseURI;
		//String baseURI = prop.getProperty("baseURI"); //Now we don't need to read it from properties file, as we're using TestNG to do that @Parameters annotation
	//	restClient = new RestClient(prop, baseURI);
		
		
		
	}

}
