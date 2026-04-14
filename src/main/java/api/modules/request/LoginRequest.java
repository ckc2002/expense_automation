package api.modules.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	//Using the Lombok to optimize the code 
    private String username;
    private String password;

    //This is the manual way of writing way
//    public LoginRequest() {
//    }
//
//    public LoginRequest(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public LoginRequest setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public LoginRequest setPassword(String password) {
//        this.password = password;
//        return this;
//    }
}
