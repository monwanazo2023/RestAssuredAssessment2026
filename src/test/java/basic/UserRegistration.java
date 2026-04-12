package basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UserRegistration {

    @Test
    public void adminLogin() {

        String apiEndpoint = "/APIDEV/login";
        String baseURL = "https://ndosiautomation.co.za";
        String payload = "{\n" +
                "  \"email\": \"admin@gmail.com\",\n" +
                "  \"password\": \"@12345678\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();
    }

    @Test
    public void userRegistration() {

        String apiEndpoint = "/APIDEV/register";
        String baseURL = "https://ndosiautomation.co.za";
        String payload = "{\n" +
                "    \"firstName\":\"Ndi\",\n" +
                "    \"lastName\":\"Thini\",\n" +
                "    \"email\":\"ndi@gmail.com\",\n" +
                "    \"password\":\"SecurePass_123\",\n" +
                "    \"confirmPassword\":\"SecurePass_123\",\n" +
                "    \"groupId\":\"5328c91e-fc40-11f0-8e00-5000e6331276\"}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();
    }
}