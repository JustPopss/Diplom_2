import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Ingredient {

    protected List<String> listOfIngredients = new ArrayList<>();

    public void loadIngredients() {
        listOfIngredients = given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .when()
                .get(Endpoints.INGREDIENTS)
                .then()
                .extract().path("data._id");
    }

    public List<String> getIngredients(int index) {
        return listOfIngredients;
    }

}
