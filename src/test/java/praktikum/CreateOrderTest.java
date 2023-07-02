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

public class CreateOrderTest {
//    @Before
//    public void setUp() {
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
//    }

    private String token;
    UserClient userClient = new UserClient();

    //    Создание заказа:
//с авторизацией,
//с ингредиентами,
    @Test
    public void authorisedUserCanCreateOrder() {
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
                .body("success", Matchers.equalTo(true))
                .body("order.ingredients", Matchers.notNullValue());


    }

    //без авторизации,
    @Test
    public void userCanCreateOrderWithoutAuthorisation() {

        OrderClient orderClient = new OrderClient();
        String ingredient = orderClient.getIngredients().extract().jsonPath().get("data[0]._id");
        List<String> ingredients = new ArrayList<String>();
        ingredients.add(ingredient);
        OrderCreate order = new OrderCreate(ingredients);
        orderClient.createWithoutAuthorisation(order)
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .body("order.number", Matchers.notNullValue());


    }

    //без ингредиентов,
    @Test
    public void authorisedUserCanNotCreateOrderWithoutIngredients() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");

        OrderClient orderClient = new OrderClient();
        List<String> ingredients = new ArrayList<String>();
        OrderCreate order = new OrderCreate(ingredients);
        orderClient.create(order, token)
                .statusCode(400)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));


    }

    //с неверным хешем ингредиентов.
    @Test
    public void authorisedUserCanNotCreateOrderWithIncorrectIngredients() {
        User user = UserProvider.getRandomUser();
        token = userClient.register(user)
                .statusCode(200)
                .extract().jsonPath().get("accessToken");

        OrderClient orderClient = new OrderClient();
        List<String> ingredients = new ArrayList<String>();
        ingredients.add("1");
        OrderCreate order = new OrderCreate(ingredients);
        orderClient.create(order, token)
                .statusCode(500);


    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.delete(token)
                    .statusCode(202);
        }
    }

}
