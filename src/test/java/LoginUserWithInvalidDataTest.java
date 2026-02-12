import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserWithInvalidDataTest {

    private final Utility utility = new Utility();
    UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, UserData.NAME);

    @Before
    @Step("Create new User")
    public void createNewUser() {
        utility.createNewUserStatus200(userData);
    }

    @Test
    @Step("Login user with incorrect email")
    public void loginUserWithIncorrectEmailTest() {
        UserData userData = new UserData(UserData.EMAIL + "1111",
                UserData.PASSWORD,
                UserData.NAME);
        utility.loginUserInvalidDataStatus401(userData);
    }

    @Test
    @Step("Login user with incorrect password")
    public void loginUserWithIncorrectPasswordTest() {
        UserData userData = new UserData(UserData.EMAIL,
                UserData.PASSWORD + "111",
                UserData.NAME);
        utility.loginUserInvalidDataStatus401(userData);
    }

    @Test
    @Step("Login user with null password")
    public void loginUserWithNullPasswordTest() {
        UserData userData = new UserData(UserData.EMAIL,
                null,
                UserData.NAME);
        utility.loginUserInvalidDataStatus401(userData);
    }

    @Test
    @Step("Login user with null name")
    public void loginUserWithNullEmailTest() {
        UserData userData = new UserData(null,
                UserData.PASSWORD,
                UserData.NAME);
        utility.loginUserInvalidDataStatus401(userData);
    }

    @After
    @Step("Delete user")
    public void deleteUser() {
        utility.deleteUserStatus202();
    }
}
