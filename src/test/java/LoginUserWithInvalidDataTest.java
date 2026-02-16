import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;

public class LoginUserWithInvalidDataTest {

    private final Utility utility = new Utility();
    private String accessToken;
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Before
    @DisplayName("Create new user")
    public void createNewUser() {
        Response responseToken = utility.createNewUser(userData);
        accessToken = utility.extractToken(responseToken);
    }

    @Test
    @DisplayName("Login user with incorrect email")
    public void loginUserWithIncorrectEmailTest() {
        UserData userData = new UserData(
                UserData.EMAIL + "1111",
                UserData.PASSWORD,
                UserData.NAME
        );

        Response response = utility.loginUserInvalidDataStatus401(userData);

        response.then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .log().status()
                .log().body()
                .log().headers();
    }

    @Test
    @DisplayName("Login user with incorrect password")
    public void loginUserWithIncorrectPasswordTest() {
        UserData userData = new UserData(
                UserData.EMAIL,
                UserData.PASSWORD + "111",
                UserData.NAME
        );

        Response response = utility.loginUserInvalidDataStatus401(userData);

        response.then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .log().status()
                .log().body()
                .log().headers();
    }

    @Test
    @DisplayName("Login user with null password")
    public void loginUserWithNullPasswordTest() {
        UserData userData = new UserData(
                UserData.EMAIL,
                null,
                UserData.NAME
        );

        Response response = utility.loginUserInvalidDataStatus401(userData);

        response.then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .log().status()
                .log().body()
                .log().headers();
    }

    @Test
    @DisplayName("Login user with null name")
    public void loginUserWithNullEmailTest() {
        UserData userData = new UserData(
                null,
                UserData.PASSWORD,
                UserData.NAME
        );

        Response response = utility.loginUserInvalidDataStatus401(userData);

        response.then()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .log().status()
                .log().body()
                .log().headers();
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
