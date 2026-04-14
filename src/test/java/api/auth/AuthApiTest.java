package api.auth;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.builder.LoginRequestBuilder;
import api.modules.request.LoginRequest;
import api.modules.response.APIResponse;
import api.modules.response.LoginResponse;
import api.services.AuthService;
import base.BaseAPITest;
import io.restassured.response.Response;

public class AuthApiTest extends BaseAPITest {

    private final AuthService authService = new AuthService();
   
    @Test(description = "Verify login API returns token for valid credentials", groups={"smoke"})
    public void verifyLoginApiReturnsToken() {
    	APIResponse<LoginResponse> response = authService.login(
    				LoginRequestBuilder.validLoginRequest()
    			);   
        
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(response.getBody().getToken());
        Assert.assertFalse(response.getBody().getToken().isBlank());
    }
    
    @Test(description = "Verify login API returns token for valid credentials", groups={"smoke"})
    public void verifyInvalidLoginDoesNotReturnToken() {
    	APIResponse<LoginResponse> response = authService.login(
				LoginRequestBuilder.invalidPasswordRequest()
			);   
    	
        Assert.assertEquals(response.getStatusCode(), 401);
    }
}
