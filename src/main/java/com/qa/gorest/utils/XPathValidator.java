package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XPathValidator {
	//Utility for XML based XPaths like "**.findAll { it.@circuitId == 'americas'}.Location.Locality""
	
	/*
	 * In JsonPathValidator, we were using Jayway JsonPath
	 * Here we're using XPath in RestAssured
	 */
	
	//Similar methods as JsonPathValidator
	
	public <T> T read(Response response, String xmlPathExpression) {
		
		XmlPath xmlPath = getXmlPathAsString(response);
		return xmlPath.get(xmlPathExpression);
	}
	
	
	//List<T> --> T=Any type of data
	public <T> List<T> readList(Response response, String xmlPathExpression) {
		XmlPath xmlPath = getXmlPathAsString(response);
		return xmlPath.get(xmlPathExpression);
	}
	
	//List<Map<String,Object>> | Key will always be String in our case
	//This method will hardly be used
	public <T> List<Map<String, T>> readListOfMaps(Response response, String xmlPathExpression) {
		
		XmlPath xmlPath = getXmlPathAsString(response);
		return xmlPath.get(xmlPathExpression);
	}
	
	private XmlPath getXmlPathAsString(Response response) {
		String responseBody = response.body().asString();
		return new XmlPath(responseBody);
	}
	

}
