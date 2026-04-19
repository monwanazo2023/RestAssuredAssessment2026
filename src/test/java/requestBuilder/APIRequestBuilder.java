package requestBuilder;


import io.restassured.RestAssured;
import io.restassured.response.Response;

import static common.BaseURI.baseURL;
import static payloadBuilder.PayloadBuilder.userLoginPayload;
import static payloadBuilder.PayloadBuilder.userRegistrationPayload;


public class APIRequestBuilder {

    static String authToken;
    static String userId;

    public static Response loggedInUser(String email, String password) {

        String apiEndpoint = "/APIDEV/login";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .body(userLoginPayload(email, password))
                .log().all()
                .post()
                .then()
                .extract().response();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 200 : "Status code should be 200";
        System.out.println("Admin login successful. Status code: " + actualStatusCode);

        authToken = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response registerUser(String firstName, String lastName, String email, String password, String groupId) {

        String apiEndpoint = "/APIDEV/register";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(userRegistrationPayload(firstName, lastName, email, password, groupId))
                .log().all()
                .post()
                .then()
                .extract().response();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 201 : "Status code should be 201";
        System.out.println("User registration successful. Status code: " + actualStatusCode);
        userId = response.jsonPath().getString("data.id");
        System.out.println("Registered user ID: " + userId);

        userId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response approveRegisteredUser() {

        String apiEndpoint = "/APIDEV/admin/users/" + userId + "/approve";

        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()
                .put()
                .then()
                .extract().response();
    }
}

