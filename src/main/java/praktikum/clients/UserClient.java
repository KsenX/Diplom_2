package praktikum.clients;

import io.restassured.response.ValidatableResponse;
import praktikum.pojo.Login;
import praktikum.pojo.User;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {
    public ValidatableResponse register(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post("/api/auth/register")
                .then();

    }

    public ValidatableResponse login(Login login) {
        return given()
                .spec(getSpec())
                .body(login)
                .when()
                .post("/api/auth/login")
                .then();

    }

    public ValidatableResponse change(User user, String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then();

    }

    public ValidatableResponse changeWithoutToken(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .patch("/api/auth/user")
                .then();

    }

    public ValidatableResponse delete(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .when()
                .delete("/api/auth/user")
                .then();

    }

}
