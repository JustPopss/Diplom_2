import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Utility {

    protected String accessToken;

    @Step("Create new User")
    public Response createNewUser(UserData userData) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .body(userData)
                .post(Endpoints.CREATE_USER);
    }

    @Step("Extract token")
    public String extractToken(Response response) {
        return response.path("accessToken");
    }

    @Step("Delete user")
    public void deleteUserStatus202(String accessToken) {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + accessToken)
                .delete(Endpoints.USER_DATA)
                .then()
                .body("success", is(true))
                .body("message", is("User successfully removed"))
                .statusCode(HTTP_ACCEPTED)
                .log().status()
                .log().body();
    }

    @Step("Create new User without fields")
    public Response createUserWithInvalidDataStatus403(UserData userData) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post(Endpoints.CREATE_USER);
    }

    @Step("Login user")
    public Response loginNewUserStatus200(UserData userData) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .log().headers()
                .contentType(ContentType.JSON)
                .body(userData)
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.AUTHORIZATION);
    }


    @Step("Login user with incorrect fields")
    public Response loginUserInvalidDataStatus401(UserData userData) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .log().headers()
                .contentType(ContentType.JSON)
                .body(userData)
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.AUTHORIZATION);
    }

    @Step("Create order with auth and without ingredients")
    public Response createOrderWithAutAndWithoutIngredientsStatus400(String accessToken) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.CREATE_ORDER);
    }

    @Step("Create order with auth and with ingredients")
    public Response createOrderWithAutAndWithIngredientsStatus200(String accessToken) {
        Ingredient ingredient = new Ingredient();
        ingredient.loadIngredients();

        List<String> ids = Arrays.asList(
                ingredient.listOfIngredients.get(1),
                ingredient.listOfIngredients.get(3),
                ingredient.listOfIngredients.get(7)
        );

        OrderRequest order = new OrderRequest(ids);

        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .body(order)
                .when()
                .post(Endpoints.CREATE_ORDER);
    }

    @Step("Create order with auth and wrong ingredients")
    public Response createOrderWithAuthAndWithWrongHashIngredientStatus500() {
        List<String> wrongIds = Arrays.asList(null, "Кто здесь?", "12345");

        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .body(Map.of("ingredients", wrongIds))
                .when()
                .post(Endpoints.CREATE_ORDER);
    }

    @Step("Create order without auth token")
    public Response createOrderWithoutAuthStatus401() {
        Ingredient ingredient = new Ingredient();
        ingredient.loadIngredients();

        List<String> ids = Arrays.asList(
                ingredient.listOfIngredients.get(1),
                ingredient.listOfIngredients.get(3),
                ingredient.listOfIngredients.get(7)
        );

        OrderRequest order = new OrderRequest(ids);

        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(Endpoints.CREATE_ORDER);
    }
}
