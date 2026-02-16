import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static org.hamcrest.Matchers.is;

public class CreateNewUserWithInvalidDataTest {

    private final Utility utility = new Utility();


    @Test
    @DisplayName("Create new User without email field")
    public void createNewUserWithoutEmail() {
        UserData userData = new UserData(
                null,
                UserData.PASSWORD,
                UserData.NAME
        );

        Response response = utility.createUserWithInvalidDataStatus403(userData);

        response.then()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .statusCode(HTTP_FORBIDDEN)
                .log().status()
                .log().body();
    }

    @Test
    @DisplayName("Create new User without password field")
    public void createNewUserWithoutPassword() {
        UserData userData = new UserData(
                UserData.EMAIL,
                null,
                UserData.NAME
        );

        Response response = utility.createUserWithInvalidDataStatus403(userData);

        response.then()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .statusCode(HTTP_FORBIDDEN)
                .log().status()
                .log().body();
    }

    @Test
    @DisplayName("Create new User without name field")
    public void createNewUserWithoutName() {
        UserData userData = new UserData(
                UserData.EMAIL,
                UserData.PASSWORD,
                null
        );

        Response response = utility.createUserWithInvalidDataStatus403(userData);

        response.then()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .statusCode(HTTP_FORBIDDEN)
                .log().status()
                .log().body();
    }
}
