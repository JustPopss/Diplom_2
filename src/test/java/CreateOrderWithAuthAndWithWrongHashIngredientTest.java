import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;

public class CreateOrderWithAuthAndWithWrongHashIngredientTest {

    private final Utility utility = new Utility();
    private String accessToken;
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Before
    @DisplayName("Create new User for token")
    public void createNewUser() {
        Response responseToken = utility.createNewUser(userData);
        accessToken = utility.extractToken(responseToken);
    }

    @Test
    @DisplayName("Create order with auth and wrong ingredients")
    public void createOrderWithAuthAndWithWrongHashIngredient() {
        Response response = utility.createOrderWithAuthAndWithWrongHashIngredientStatus500();

        response.then()
                .statusCode(HTTP_SERVER_ERROR)
                .log().status()
                .log().body();
    }

    @After
    @DisplayName("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202(accessToken);
    }
}
