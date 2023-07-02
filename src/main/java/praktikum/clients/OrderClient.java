package praktikum.clients;

import io.restassured.response.ValidatableResponse;
import praktikum.pojo.OrderCreate;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/ingredients")
                .then();

    }

    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/orders/all")
                .then();

    }

    public ValidatableResponse getUserOrders(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .when()
                .get("/api/orders")
                .then();

    }

    public ValidatableResponse getUserOrdersWithoutAuthorisation() {
        return given()
                .spec(getSpec())
                .when()
                .get("/api/orders")
                .then();

    }

    public ValidatableResponse create(OrderCreate order, String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .body(order)
                .when()
                .post("/api/orders")
                .then();

    }

    public ValidatableResponse createWithoutAuthorisation(OrderCreate order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post("/api/orders")
                .then();

    }


}
