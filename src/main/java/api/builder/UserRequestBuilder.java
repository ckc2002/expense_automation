package api.builder;

import api.modules.request.CreateUserRequest;

public class UserRequestBuilder {
	 private UserRequestBuilder() {
	    }

	  public static CreateUserRequest createDefaultUser() {
	       return CreateUserRequest.builder()
	                .id(255)
	                .username("SDET")
	                .email("sdet@gmail.com")
	                .build();
	  }

//	  public static CreateUserRequest createUpdatedUser() {
//	      return new CreateUserRequest()
//	                .setName("Peter Updated")
//	                .setJob("Senior SDET");
//	  }
}
