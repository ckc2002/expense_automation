package api.services;

import api.clients.APIClient;
import api.clients.RequestSpecFactory;
import api.modules.request.CreateUserRequest;
import api.modules.response.CreateUserResponse;
import io.restassured.response.Response;
import utils.Endpoints;

public class UserService {
	public Response createUser(CreateUserRequest request) {
        return APIClient.post(
                Endpoints.USERS,
                request,
                RequestSpecFactory.defaultSpec()
        );
    }

    public CreateUserResponse createUserAndGetResponse(CreateUserRequest request) {
        return createUser(request).as(CreateUserResponse.class);
    }

    public Response getUserById(int userId) {
        return APIClient.get(
                String.format(Endpoints.USER_BY_ID, userId),
                RequestSpecFactory.defaultSpec()
        );
    }

    public Response updateUser(int userId, CreateUserRequest request) {
        return APIClient.put(
                String.format(Endpoints.USER_BY_ID, userId),
                request,
                RequestSpecFactory.defaultSpec()
        );
    }

    public Response deleteUser(int userId) {
        return APIClient.delete(
                String.format(Endpoints.USER_BY_ID, userId),
                RequestSpecFactory.defaultSpec()
        );
    }
}
