package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

public class ConfigurationManager {

	
	//This will help me to read a code from Properties class
	
	//what are the different classes to read the properties files?
	//Properties file
	//File Input stream
	
	private Properties prop;
	private FileInputStream ip;
	
	public Properties initProp() {
		
		prop = new Properties();
		
		//Multi-env support--qa,dev,stage
		
		//maven: cmd line argument:
		//mvn clean install -Denv="qa"/"stage"/"dev"
		//In Java, we've to read this particular env variable by System class
		
		String envName = System.getProperty("env");
		try {
		if(envName == null) {
			//default env--qa
			System.out.println("No env. is given. Hence running tests on QA env...");
			ip = new FileInputStream("./src/test/resource/config/qa.config.properties");
		}
		else {
			System.out.println("Running tests on env: " + envName);
			
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resource/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resource/config/dev.config.properties");
				break;
			case "stage":
				ip = new FileInputStream("./src/test/resource/config/stage.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resource/config/config.properties");
				break;

			default:
				System.out.println("Please pass right env. name " + envName);
				//throw custom exception
				throw new APIFrameworkException("WRONG ENV IS GIVEN");
				}
		}
		}	
		catch(FileNotFoundException e) {
				e.printStackTrace();
		}
		
		//load the prop
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
	

}
