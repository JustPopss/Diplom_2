import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Utility {

    protected String accessToken;

    public void createNewUserStatus200(UserData userData) {
        accessToken = given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post(Endpoints.CREATE_USER)
                .then()
                .log().body()
                .log().status()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .body("accessToken", is(notNullValue()))
                .extract().path("accessToken");
    }

    public void deleteUserStatus202() {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + accessToken)
                .delete(Endpoints.USER_DATA)
                .then()
                .log().body()
                .log().status()
                .body("success", is(true))
                .body("message", is("User successfully removed"))
                .statusCode(HTTP_ACCEPTED);
    }

    public void createDoubleUserStatus403(UserData userData) {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post(Endpoints.CREATE_USER)
                .then()
                .log().body()
                .log().status()
                .body("success", is(false))
                .body("message", is("User already exists"))
                .statusCode(HTTP_FORBIDDEN);
    }

    public void createUserWithInvalidDataStatus403(UserData userData) {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post(Endpoints.CREATE_USER)
                .then()
                .log().body()
                .log().status()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .statusCode(HTTP_FORBIDDEN);
        // добавить текст
    }

    public void loginNewUserStatus200(UserData userData) {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .log().headers()
                .contentType(ContentType.JSON)
                .body(userData)
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.AUTHORIZATION)
                .then()
                .log().body()
                .log().headers()
                .log().status()
                .body("success", is(true))
                .body("accessToken", is(notNullValue()))
                .body("user.email", is(UserData.EMAIL))
                .statusCode(HTTP_OK);
    }

    public void loginUserInvalidDataStatus401(UserData userData) {
        given()
                .baseUri(Endpoints.BASE_URI)
                .log().body()
                .log().headers()
                .contentType(ContentType.JSON)
                .body(userData)
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.AUTHORIZATION)
                .then()
                .log().body()
                .log().headers()
                .log().status()
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .statusCode(HTTP_UNAUTHORIZED);
    }

    public void createOrderWithAutAndWithoutIngredientsStatus400() {
        given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .when()
                .post(Endpoints.CREATE_ORDER)
                .then()
                .log().body()
                .log().status()
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
                .statusCode(HTTP_BAD_REQUEST);
    }

    public void createOrderWithAutAndWithIngredientsStatus200() {
        Ingredient ingredient = new Ingredient();
        ingredient.getIngredients();

        List<String> ids = Arrays.asList(
                ingredient.listOfIngredients.get(1),
                ingredient.listOfIngredients.get(3),
                ingredient.listOfIngredients.get(7));

        given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .body(Map.of("ingredients", ids))
                .when()
                .post(Endpoints.CREATE_ORDER)
                .then()
                .log().body()
                .log().status()
                .body("success", is(true))
                .body("order.owner.name", is(notNullValue()))
                .statusCode(HTTP_OK);
    }

    public void createOrderWithAuthAndWithWrongHashIngredientStatus500() {
        List<String> wrongIds = Arrays.asList(null, "Кто здесь?", "12345");

        given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .log().all()
                .header("Authorization", "Bearer" + accessToken)
                .body(Map.of("ingredients", wrongIds))
                .when()
                .post(Endpoints.CREATE_ORDER)
                .then()
                .log().body()
                .log().status()
                .statusCode(HTTP_SERVER_ERROR);
    }

    public void createOrderWithoutAuthStatus401() {
        Ingredient ingredient = new Ingredient();
        ingredient.getIngredients();

        List<String> ids = Arrays.asList(
                ingredient.listOfIngredients.get(1),
                ingredient.listOfIngredients.get(3),
                ingredient.listOfIngredients.get(7));

        given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .body(Map.of("ingredients", ids))
                .get(Endpoints.CREATE_ORDER)
                .then()
                .log().body()
                .log().status()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .statusCode(HTTP_UNAUTHORIZED);
    }
}
