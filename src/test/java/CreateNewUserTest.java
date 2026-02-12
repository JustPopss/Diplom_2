import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Test;


public class CreateNewUserTest {

    private final Utility utility = new Utility();
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Test
    @Step("Create new User")
    public void createNewUser() {
        utility.createNewUserStatus200(userData);
    }

    @After
    @Step("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202();
    }
}
