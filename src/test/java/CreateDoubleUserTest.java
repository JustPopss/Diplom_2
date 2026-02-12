import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateDoubleUserTest {

    private final Utility utility = new Utility();
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Before
    @Step("Create new User")
    public void createNewUser() {
        utility.createNewUserStatus200(userData);
    }

    @Test
    @Step("Try to create user with same data")
    public void createDoubleUser() {
        utility.createDoubleUserStatus403(userData);
    }

    @After
    @Step("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202();
    }
}
