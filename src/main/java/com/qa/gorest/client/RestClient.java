package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	//Will have number of methods like GET, POST, PUT, DELETE
	
//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "55b2c799844a831c6401e7b4178327f0a3c5c12f66359af247c473a18cd5fe76";
	
	private static RequestSpecBuilder specBuilder;
	
	private Properties prop;
	private String baseURI;
	
	private boolean isAuthorizationAdded = false;
	
	//Create const
	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();
		this.prop = prop;
		this.baseURI = baseURI;
	}
	
	//creating one common auth method so we can use it whenever we want it
	public void addAuthorizationHeader() {
		
		if(!isAuthorizationAdded) {
			specBuilder.addHeader("Authorization","Bearer " + prop.getProperty("tokenId"));
			isAuthorizationAdded = true;
		}
	}
	
	//private methods used by public method(get) --Encapsulation
	//utility for content type
	private void setRequestContentType(String contentType) { 
		switch (contentType.toLowerCase()) {//json-JSON-Json
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;

		default:
			System.out.println("Please pass the right content type.");
			throw new APIFrameworkException("INVALIDCONTENTTYPE");
		}
	}
	
	//Basic request
	private RequestSpecification createRequestSpec(boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);   //will be coming from config file
		
		if(includeAuth) {
			addAuthorizationHeader();
		}
		
		return specBuilder.build();	
	}
	
	//Method overloading
	//Basic req + header map/extra headers
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);   //will be coming from config file
		
		if(includeAuth) {
			addAuthorizationHeader();
		}
		
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();	
	}
	
	//Method overloading
	//query param
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth) {
			addAuthorizationHeader();
		}
		
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams!=null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();	
	}
	
	
	//***Content-type***---GET call
	//Method overloading
	//post
	//Object requestBody --POJO --lombok class
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
			
			specBuilder.setBaseUri(baseURI);
			if(includeAuth) {
				addAuthorizationHeader();
			}
			setRequestContentType(contentType); //from above utility method-
			
			if(requestBody!=null) {
				specBuilder.setBody(requestBody);
			}
			return specBuilder.build();	
		}
	
	//Method overloading
	//we can pass header also along with POJO and content type
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap, boolean includeAuth) {
		
		specBuilder.setBaseUri(baseURI);
		
		//Auth header
		if(includeAuth) {
			addAuthorizationHeader();
		}
		
		//Header map
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();	
	}
	
	//*****HTTP METHODS*******
	//HTTP Methods Utils --Encapsulation--public methods
	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		
		if(log) {
			return	RestAssured.given(createRequestSpec(includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		return	RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
	}
	
	//Method Overloading 
	//Having headersmap
	public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {
		
		if(log) {
			return	RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
			.when()
				.get(serviceUrl);
		}
		return	RestAssured.given(createRequestSpec(headersMap, includeAuth))
			.when()
				.get(serviceUrl);
	}
	
	//MEthod Overloading 
		//Having query params also
	public Response get(String serviceUrl, Map<String, Object> queryParams, Map<String, String> headersMap, boolean includeAuth, boolean log) {
			
			if(log) {
				return	RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
				.when()
					.get(serviceUrl);
			}
			return	RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
		}
	
	
	//****POST Call****
	
	//Without header
	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		
		if(log) {
			return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
						.when()
							.post(serviceUrl);
		}
		return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
				.when()
					.post(serviceUrl);
	}
	
	//No query param. with POST call | With header | Method Overloaded
	public Response post(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
		
		if(log) {
			return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
						.when()
							.post(serviceUrl);
		}
		return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
				.when()
					.post(serviceUrl);
	}
	
	//***PUT Call***
	//Without header
		public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
			
			if(log) {
				return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
							.when()
								.put(serviceUrl);
			}
			return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
					.when()
						.put(serviceUrl);
		}
		
		//No query param. with POST call | With header | Method Overloaded
		public Response put(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
			
			if(log) {
				return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
							.when()
								.put(serviceUrl);
			}
			return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
					.when()
						.put(serviceUrl);
		}
		
		//***PATCH Call***
		//Without header
		public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
					
			if(log) {
					return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
								.when()
									.patch(serviceUrl);
			}
					return	RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth))
							.when()
								.patch(serviceUrl);
		}
				
		//No query param. with POST call | With header | Method Overloaded
		public Response patch(String serviceUrl, String contentType, Object requestBody, Map<String, String> headersMap, boolean includeAuth, boolean log) {
					
			if(log) {
				return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth)).log().all()
							.when()
								.patch(serviceUrl);
			}
				return	RestAssured.given(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
							.when()
								.patch(serviceUrl);
		}
		
		//***Delete Call****
		public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
			if(log) {
				return	RestAssured.given(createRequestSpec(includeAuth)).log().all()
							.when()
								.delete(serviceUrl);
			}
				return	RestAssured.given(createRequestSpec(includeAuth))
							.when()
								.delete(serviceUrl);
		}
		
		
		//OAuth 2
		public String getAccessToken(String serviceUrl, String grantType, String clientID, String clientSecret) {
			//1. POST - Get the access token   
					RestAssured.baseURI = "https://test.api.amadeus.com";
					
							String	accessToken = 	given().log().all()
											.contentType(ContentType.URLENC)
											.formParam("grant_type", grantType)
											.formParam("client_id", clientID)
											.formParam("client_secret", clientSecret)
										.when()
											.post(serviceUrl)
										.then().log().all()
											.assertThat()
												.statusCode(APIHttpStatus.OK_200.getCode())
													.extract().path("access_token");
						
					System.out.println("Access token: " + accessToken); //E.g. - noV52JOFG64Zt03eEsReDXSf6gBw
					return accessToken;
		}

}
