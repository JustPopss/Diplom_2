import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserWithValidDataTest {

    private final Utility utility = new Utility();
    private String accessToken;
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
    @DisplayName("Login user")
    public void loginNewUser() {
        Response response = utility.loginNewUserStatus200(userData);

        response.then()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .body("accessToken", is(notNullValue()))
                .log().status()
                .log().body();
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
