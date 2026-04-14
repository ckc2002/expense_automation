package api.clients;

import config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {
	
	private RequestSpecFactory() {
		
	}
	
	 public static RequestSpecification defaultSpec() {
		 String apiUrl = ConfigManager.getProperty("apiURL");

	        return new RequestSpecBuilder()
	                .setBaseUri(apiUrl)
	                .setContentType(ContentType.JSON)
	                .build();
	 }
	 
	 public static RequestSpecification authorizedSpec(String token) {
	        return new RequestSpecBuilder()
	                .setBaseUri(ConfigManager.getProperty("apiURL"))
	                .setContentType(ContentType.JSON)
	                .addHeader("Authorization", "Bearer " + token)
	                .build();
	    }
}
