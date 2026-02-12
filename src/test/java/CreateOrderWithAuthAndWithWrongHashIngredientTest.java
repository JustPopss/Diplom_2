import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderWithAuthAndWithWrongHashIngredientTest {
    private final Utility utility = new Utility();
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Before
    @Step("Create new User for token")
    public void createNewUser() {
        utility.createNewUserStatus200(userData);
    }

    @Test
    @Step("Create order with auth and wrong ingredients")
    public void createOrderWithAuthAndWithWrongHashIngredient() {
        utility.createOrderWithAuthAndWithWrongHashIngredientStatus500();
    }

    @After
    @Step("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202();
    }
}
