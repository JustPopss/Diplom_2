import io.qameta.allure.Step;
import org.junit.Test;

public class CreateNewUserWithInvalidDataTest {

    private final Utility utility = new Utility();


    @Test
    @Step("Create new User without email field")
    public void createNewUserWithoutEmail() {
        UserData userData = new UserData(null, UserData.PASSWORD, UserData.NAME);
        utility.createUserWithInvalidDataStatus403(userData);
    }

    @Test
    @Step("Create new User without password field")
    public void createNewUserWithoutPassword() {
        UserData userData = new UserData(UserData.EMAIL, null, UserData.NAME);
        utility.createUserWithInvalidDataStatus403(userData);
    }

    @Test
    @Step("Create new User without name field")
    public void createNewUserWithoutName() {
        UserData userData = new UserData(UserData.EMAIL, UserData.PASSWORD, null);
        utility.createUserWithInvalidDataStatus403(userData);
    }

}
