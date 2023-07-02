package praktikum;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import praktikum.clients.UserClient;
import praktikum.dataprovider.LoginProvider;
import praktikum.dataprovider.UserProvider;
import praktikum.pojo.Login;
import praktikum.pojo.User;

public class LoginUserTest {
//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private String token;
    UserClient userClient = new UserClient();

    //    Логин пользователя:
//логин под существующим пользователем,
    @Test
    public void userCanLogin() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");
        Login login = LoginProvider.getLogin(user.getEmail(), user.getPassword());

        userClient.login(login)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("user.email", Matchers.equalTo(user.getEmail().toLowerCase()));


    }

//логин с неверным логином и паролем.

    @Test
    public void invalidUserCanNotLogin() {
        User user = UserProvider.getRandomUser();
        Login login = LoginProvider.getLogin(user.getEmail(), user.getPassword());

        userClient.login(login)
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("email or password are incorrect"));


    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.delete(token)
                    .statusCode(202);
        }
    }
}
