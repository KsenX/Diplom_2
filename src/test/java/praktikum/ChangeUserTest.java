package praktikum;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import praktikum.clients.UserClient;
import praktikum.dataprovider.UserProvider;
import praktikum.pojo.User;

public class ChangeUserTest {

//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private String token;
    UserClient userClient = new UserClient();

    //    Изменение данных пользователя:
//с авторизацией,
    @Test
    public void userCanChangeAllData() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");
        User userChanged = UserProvider.getRandomUser();

        userClient.change(userChanged, token)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath().get("user.email").equals(userChanged.getEmail());


    }

    //без авторизации,
//Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.
    @Test
    public void notAuthorisedUserCanNotChangeData() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");
        User userChanged = UserProvider.getRandomUser();

        userClient.changeWithoutToken(userChanged)
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("You should be authorised"));
        ;


    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.delete(token)
                    .statusCode(202);
        }
    }
}
