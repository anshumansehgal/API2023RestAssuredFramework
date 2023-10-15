package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class ReqResAPITest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetUp() {
		restClient = new RestClient(prop, baseURI);
	}
		
		@Test
		public void getReqRespUsersTest() {
			
			restClient.get(REQRES_ENDPOINT, false, false)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		}

}
