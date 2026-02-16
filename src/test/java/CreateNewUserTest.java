import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


public class CreateNewUserTest {

    private String accessToken;
    private final Utility utility = new Utility();
    UserData userData = new UserData(
            UserData.EMAIL,
            UserData.PASSWORD,
            UserData.NAME
    );

    @Test
    @DisplayName("Create new User and get token")
    public void createNewUser() {
        Response response = utility.createNewUser(userData);

        response.then()
                .statusCode(HTTP_OK)
                .log().body()
                .log().status()
                .body("success", is(true))
                .body("accessToken", is(notNullValue()));

        accessToken = utility.extractToken(response);
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
