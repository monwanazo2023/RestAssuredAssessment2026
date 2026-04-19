package basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UserRegistration {

    static String authToken;
    static String userId;
    static String registerEmail;
    static String baseURL = "https://ndosiautomation.co.za";

    @Test
    public void adminLogin() {

        String apiEndpoint = "/APIDEV/login";
//        String baseURL = "https://ndosiautomation.co.za";
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

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 200 : "Status code should be 200";
        System.out.println("Admin login successful. Status code: " + actualStatusCode);
        authToken = response.jsonPath().getString("data.token");
    }

    @Test(priority = 2)
    public void userRegistration() {

        String apiEndpoint = "/APIDEV/register";
        registerEmail = Faker.instance().internet().emailAddress();
        String firstName = Faker.instance().name().firstName();
        String lastName = Faker.instance().name().lastName();
//        String baseURL = "https://ndosiautomation.co.za";
        String payload = "{\n" +
                "    \"firstName\":\"" + firstName + "\",\n" +
                "    \"lastName\":\"" + lastName + "\",\n" +
                "    \"email\":\"" + registerEmail + "\",\n" +
                "    \"password\":\"SecurePass_123\",\n" +
                "    \"confirmPassword\":\"SecurePass_123\",\n" +
                "    \"groupId\":\"5328c91e-fc40-11f0-8e00-5000e6331276\"}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 201 : "Status code should be 201";
        System.out.println("User registration successful. Status code: " + actualStatusCode);
        userId = response.jsonPath().getString("data.id");
        System.out.println("Registered user ID: " + userId);
    }

    @Test(priority = 3)
    public void approveUserRegistration() {

        String apiEndpoint = "/APIDEV/admin/users/" + userId + "/approve";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()
                .put().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 200 : "Status code should be 200";
        System.out.println("User approval successful. Status code: " + actualStatusCode);
    }

    @Test(priority = 4)
    public void loginRegisteredUser() {

        String apiEndpoint = "/APIDEV/login";
        String payload = String.format( "{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"password\": \"SecurePass_123\"\n" +
                "}", registerEmail);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiEndpoint)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        assert actualStatusCode == 200 : "Status code should be 200";
        System.out.println("User logged in successful. Status code: " + actualStatusCode);
    }
}