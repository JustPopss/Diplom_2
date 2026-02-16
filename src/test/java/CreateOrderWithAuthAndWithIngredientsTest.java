import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderWithAuthAndWithIngredientsTest {

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
    @DisplayName("Create order with auth and with ingredients")
    public void createOrderWithAuthAndWithIngredientsStatus200() {
        Response response = utility.createOrderWithAutAndWithIngredientsStatus200(accessToken);

        response.then()
                .statusCode(HTTP_OK)
                .log().headers()
                .body("success", is(true))
                .body("order.owner.name", is(notNullValue()))
                .log().status()
                .log().body();
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
