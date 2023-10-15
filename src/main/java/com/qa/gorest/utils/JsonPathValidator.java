package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.response.Response;

public class JsonPathValidator {
	//Generic solution for JSON Path 
	
	//1. T --fetching single value e.g. ID
	public <T> T read(Response response, String jsonPath) {//Instead of void we can write Object or <T> T --any type of return--String/Integer/Float/Double
		
		String jsonResponse = getJsonResponseAsString(response);
		
		//jsonPath from Jayway--while reading it it can give some exception so handle with try and catch
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) { //PathNotFoundException--pre-defined in Jayway JsonPath
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found.");
		}
	}
	
	
	//List<T> --> T=Any type of data---2 values--List<countries>
	public <T> List<T> readList(Response response, String jsonPath) {
		
		String jsonResponse = getJsonResponseAsString(response);
		
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found.");
		}
	}
	
	//List<Map<String,Object>> | Key will always be String in our case | 3 keys--hashMap
	public <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {
		
		String jsonResponse = getJsonResponseAsString(response);
		
		try {
			return JsonPath.read(jsonResponse, jsonPath);
		}
		catch(PathNotFoundException e) {
			e.printStackTrace();
			throw new APIFrameworkException(jsonPath + "is not found.");
		}
	}
	
	private String getJsonResponseAsString(Response response) {
		return response.getBody().asString();
	}
	
}
