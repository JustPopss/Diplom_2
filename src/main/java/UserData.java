import com.github.javafaker.Faker;

public class UserData {

    static Faker faker = new Faker();

    private final String email;
    private final String password;
    private final String name;

    protected static final String NAME = faker.name().firstName() + System.currentTimeMillis();
    protected static final String PASSWORD = faker.regexify("[0-9]{6}");
    protected static final String EMAIL = NAME.toLowerCase() + "@pochta.ru";

    public UserData(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
