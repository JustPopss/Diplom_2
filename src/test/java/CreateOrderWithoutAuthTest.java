import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.Matchers.is;


public class CreateOrderWithoutAuthTest {
    private final Utility utility = new Utility();

    @Test
    @DisplayName("Create order without auth token")

    // Баг. Заказ создается без авторизации

    public void createOrderWithoutAuth() {
        Response response = utility.createOrderWithoutAuthStatus401();

        response.then()
                .statusCode(HTTP_UNAUTHORIZED)
                .log().status()
                .log().body()
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}