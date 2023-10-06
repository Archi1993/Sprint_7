package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    protected static String login = "Arch";
    protected static String password = "password123";
    protected static String firstName = "Archi";


    public static Courier random() {
        return new Courier("AutoTest" + RandomStringUtils.randomAlphanumeric(5, 10), password, firstName);
    }

    public static Courier generic() {
        return new Courier(login, password, firstName);
    }

    public static Courier genericWithoutLogin() {
        return new Courier(null, password, firstName);
    }

    public static Courier genericWithoutPassword() {
        return new Courier(login, null, firstName);
    }

}
