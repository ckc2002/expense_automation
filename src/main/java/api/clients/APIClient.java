package api.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIClient {
	
	 private APIClient() {
	  }
	
	public static Response get(String endpoint, RequestSpecification specification) {
        return RestAssured
                .given()
                .spec(specification)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post( String endpoint, Object body, RequestSpecification specification) {
        return RestAssured
                .given()
                .spec(specification)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body, RequestSpecification specification) {
        return RestAssured
                .given()
                .spec(specification)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint, RequestSpecification specification) {
        return RestAssured
                .given()
                .spec(specification)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}
