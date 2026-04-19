package payloadBuilder;


import org.json.simple.JSONObject;

public class PayloadBuilder {

    public static JSONObject userLoginPayload(String email, String password) {

        JSONObject userLogin = new JSONObject();
        userLogin.put("email", email);
        userLogin.put("password", password);

        return userLogin;
    }

    public static JSONObject userRegistrationPayload(String firstName, String lastName, String email, String password, String groupId) {
        JSONObject userRegistration = new JSONObject();
        userRegistration.put("firstName", firstName);
        userRegistration.put("lastName", lastName);
        userRegistration.put("email", email);
        userRegistration.put("password", password);
        userRegistration.put("confirmPassword", password);
        userRegistration.put("groupId", groupId);

        return userRegistration;
    }

}
