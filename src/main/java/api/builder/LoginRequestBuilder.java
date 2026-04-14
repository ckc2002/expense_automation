package api.builder;

import api.modules.request.LoginRequest;

public class LoginRequestBuilder {
	 private LoginRequestBuilder() {
	 }
	 
	 public static LoginRequest validLoginRequest() {
	        return  LoginRequest.builder()
	        		.username("kevinryan")
	        		.password("kev02937@")
	        		.build();
	 }
	        
	 public static LoginRequest invalidPasswordRequest() {
	        return  LoginRequest.builder()
	        		.username("kevinryanasas")
	        		.password("kev02937@2233")
	        		.build();
	  }

	  public static LoginRequest missingPasswordRequest() {
	        return  LoginRequest.builder()
	        		.username("kevinryan")
	        		.password(null)
	        		.build();
	  }
}
