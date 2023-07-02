package praktikum;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import praktikum.clients.OrderClient;
import praktikum.clients.UserClient;
import praktikum.dataprovider.UserProvider;
import praktikum.pojo.OrderCreate;
import praktikum.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class GetOrdersTest {

//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private String token;
    UserClient userClient = new UserClient();

    //    Получение заказов конкретного пользователя:
//авторизованный пользователь,
    @Test
    public void getOrdersAuthorised() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");

        OrderClient orderClient = new OrderClient();
        String ingredient = orderClient.getIngredients().extract().jsonPath().get("data[0]._id");
        List<String> ingredients = new ArrayList<String>();
        ingredients.add(ingredient);
        OrderCreate order = new OrderCreate(ingredients);
        orderClient.create(order, token)
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        orderClient.getUserOrders(token)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath().get("totalToday").equals(1);


    }

    //неавторизованный пользователь.
    @Test
    public void getOrdersNotAuthorised() {

        OrderClient orderClient = new OrderClient();


        orderClient.getUserOrdersWithoutAuthorisation()
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .extract().jsonPath().get("message").equals("You should be authorised");


    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.delete(token)
                    .statusCode(202);
        }
    }

}
