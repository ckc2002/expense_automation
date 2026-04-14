package api.modules.response;

import api.modules.request.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
	private Integer id;
    private String name;
    private String email;
    private String password;
}
