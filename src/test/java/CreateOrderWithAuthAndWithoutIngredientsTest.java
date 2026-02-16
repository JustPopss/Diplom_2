import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.hamcrest.Matchers.is;

public class CreateOrderWithAuthAndWithoutIngredientsTest {

    private final Utility utility = new Utility();
    private String accessToken;
    UserData userData = new UserData(
            UserData.EMAIL,
            UserData.PASSWORD,
            UserData.NAME
    );

    @Before
    @DisplayName("Create new User for token")
    public void createNewUser() {
        Response responseToken = utility.createNewUser(userData);
        accessToken = utility.extractToken(responseToken);
    }

    @Test
    @DisplayName("Create order with auth and without ingredients")
    public void createOrderWithAutAndWithoutIngredientsStatus400() {
        Response response = utility.createOrderWithAutAndWithoutIngredientsStatus400(accessToken);

        response.then()
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
                .statusCode(HTTP_BAD_REQUEST)
                .log().status()
                .log().body();

    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
