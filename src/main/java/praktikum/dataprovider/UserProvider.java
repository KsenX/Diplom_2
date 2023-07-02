package praktikum.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import praktikum.pojo.User;

public class UserProvider {
    public static User getRandomUser() {
        User user = new User(RandomStringUtils.randomAlphanumeric(8) + "@" +
                RandomStringUtils.randomAlphabetic(8) + "." + RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8));
        return user;
    }

    public static User getRandomUserWithoutEmail() {
        User user = new User(null, RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(8));
        return user;
    }

    public static User getRandomUserWithoutPassword() {
        User user = new User(RandomStringUtils.randomAlphanumeric(8) + "@" +
                RandomStringUtils.randomAlphabetic(8) + "." + RandomStringUtils.randomAlphabetic(2),
                null, RandomStringUtils.randomAlphabetic(8));
        return user;
    }

    public static User getRandomUserWithoutName() {
        User user = new User(RandomStringUtils.randomAlphanumeric(8) + "@" +
                RandomStringUtils.randomAlphabetic(8) + "." + RandomStringUtils.randomAlphabetic(2),
                RandomStringUtils.randomAlphabetic(8), null);
        return user;
    }
}
