import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static org.hamcrest.Matchers.is;

public class CreateDoubleUserTest {

    private String accessToken;
    private String responseOne;
    private final Utility utility = new Utility();
    UserData userData = new UserData(
            UserData.EMAIL,
            UserData.PASSWORD,
            UserData.NAME
    );

    @Before
    @DisplayName("Create new User")
    public void createNewUser() {
        Response responseToken = utility.createNewUser(userData);
        accessToken = utility.extractToken(responseToken);
    }

    @Test
    @DisplayName("Try to create user with same data")
    public void createDoubleUser() {
        Response responseTwo = utility.createNewUser(userData);

        responseTwo.then()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("User already exists"))
                .log().status()
                .log().body();
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
