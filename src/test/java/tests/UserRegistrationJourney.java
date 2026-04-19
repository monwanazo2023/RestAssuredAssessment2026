package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import requestBuilder.APIRequestBuilder;

import static org.hamcrest.core.IsEqual.equalTo;

public class UserRegistrationJourney {

    static String registrationEmail;
    static String firstName;
    static String lastName;

    @Test
    public void adminLogin() {

        APIRequestBuilder.loggedInUser("admin@gmail.com", "@12345678")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test(priority = 2)
    public void userRegistration() {
        registrationEmail = Faker.instance().internet().emailAddress();
        firstName = Faker.instance().name().firstName();
        lastName = Faker.instance().name().lastName();
        APIRequestBuilder.registerUser(firstName, lastName, registrationEmail, "@Secure_Pass", "5328c91e-fc40-11f0-8e00-5000e6331276")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("success", equalTo(true));
        ;
    }

    @Test(priority = 3)
    public void approveUserRegistration() {
        APIRequestBuilder.approveRegisteredUser()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 4)
    public void userLogin() {

        APIRequestBuilder.loggedInUser(registrationEmail, "@Secure_Pass")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

    }
}

