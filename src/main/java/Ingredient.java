import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Ingredient {

    public Ingredient() {
    }

    protected List<String> listOfIngredients = new ArrayList<>();

    public void getIngredients() {
        this.listOfIngredients = given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .get(Endpoints.INGREDIENTS)
                .then()
                .log().body()
                .log().status()
                .extract().path("data._id");
    }

}
