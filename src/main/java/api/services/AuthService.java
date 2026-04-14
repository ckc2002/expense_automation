package api.services;

import api.clients.APIClient;
import api.clients.RequestSpecFactory;
import api.modules.request.LoginRequest;
import api.modules.response.APIResponse;
import api.modules.response.LoginResponse;
import io.restassured.response.Response;
import utils.Endpoints;

public class AuthService {


    public APIResponse<LoginResponse> login(LoginRequest request) {
    	Response response =  APIClient.post(Endpoints.LOGIN, request, RequestSpecFactory.defaultSpec());
    	
    	
        return new APIResponse<>(response, LoginResponse.class);
    }
}
