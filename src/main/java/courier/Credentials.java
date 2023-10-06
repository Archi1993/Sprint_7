package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Credentials {
    private String login;
    private String password;

    public Credentials() {
    }

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Credentials from(Courier courier) {
        return new Credentials(courier.getLogin(), courier.getPassword());
    }

    public static Credentials fromWithoutLogin(Courier courier) {
        return new Credentials("", courier.getPassword());
    }

    public static Credentials fromWithoutPassword(Courier courier) {
        return new Credentials(courier.getLogin(), "");
    }

    public static Credentials fromNonExistentLogin(Courier courier) {
        return new Credentials("Test" + RandomStringUtils.randomAlphanumeric(5, 10), courier.getPassword());
    }

    public static Credentials fromNonExistentPassword(Courier courier) {
        return new Credentials(courier.getLogin(), "AutoTest" + RandomStringUtils.randomAlphanumeric(5, 10));
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}