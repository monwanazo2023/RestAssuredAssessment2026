package requestBuilder;

import common.BaseURI;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static common.BaseURI.baseURL;
import static io.restassured.RestAssured.baseURI;


public class APIRequestBuilder {

    static String authToken;
    static String registeredUserID;

    public static Response loggedInUser(String email, String password) {

        String apiEndpoint = baseURL + "/APIDEV/login";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"" + password + "\"\n" +
                        "}")
                .log().all()
                .post()
                .then()
                .extract().response();

        authToken = response.jsonPath().getString("data.token");
        return response;
    }
}

