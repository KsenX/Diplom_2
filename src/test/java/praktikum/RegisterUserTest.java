package praktikum;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import praktikum.clients.UserClient;
import praktikum.dataprovider.UserProvider;
import praktikum.pojo.User;

public class RegisterUserTest {

//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private String token;
    UserClient userClient = new UserClient();

    //    Создание пользователя:
//    создать уникального пользователя;
    @Test
    public void userCanBeCreated() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("user.email", Matchers.equalTo(user.getEmail().toLowerCase())) //приложение не учитывает регистр.. БАГ?
                .extract().jsonPath().get("accessToken");
    }

    //    создать пользователя, который уже зарегистрирован;
    @Test
    public void duplicateUserCanNotBeCreated() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath().get("accessToken");
        userClient.register(user)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("User already exists"));

    }

    //    создать пользователя и не заполнить одно из обязательных полей.
    @Test
    public void userWithoutEmailCanNotBeCreated() {
        User user = UserProvider.getRandomUserWithoutEmail();
        userClient.register(user)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    public void userWithoutPasswordCanNotBeCreated() {
        User user = UserProvider.getRandomUserWithoutPassword();
        userClient.register(user)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    public void userWithoutNameCanNotBeCreated() {
        User user = UserProvider.getRandomUserWithoutName();
        userClient.register(user)
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.delete(token)
                    .statusCode(202);
        }
    }


}
