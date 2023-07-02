package praktikum.dataprovider;

import praktikum.pojo.Login;

public class LoginProvider {
    public static Login getLogin(String email, String password) {
        Login login = new Login(email, password);
        return login;
    }
}
