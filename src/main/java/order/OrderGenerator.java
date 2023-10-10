package order;

public class OrderGenerator {

    protected static String firstName = "Naruto";
    protected static String lastName = "Uchiha";
    protected static String address = "Konoha, 142 apt.";
    protected static String metroStation = "4";
    protected static String phone = "+78003553535";
    protected static int rentTime = 5;
    protected static String deliveryDate = "2023.10.5";
    protected static String comment = "Saske, come back to Konoha)";
    protected static String[] color;

    public static Order generic() {
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }


}
