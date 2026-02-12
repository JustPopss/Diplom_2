import io.qameta.allure.Step;
import org.junit.Test;

public class CreateOrderWithoutAuthTest {
    private final Utility utility = new Utility();

    @Test
    @Step("Create order without auth token")
    public void createOrderWithoutAuth() {
        utility.createOrderWithoutAuthStatus401();
    }
}
