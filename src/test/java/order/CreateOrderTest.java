package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient client = new OrderClient();
    private final OrderAssertion check = new OrderAssertion();
    private final String[] color;


    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters()
    public static Object[][] orderColor() {
        return new Object[][]{
                {new String[]{}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
        };
    }


    @Test
    @DisplayName("Заказ самокатов всех цветов")
    @Description("Когда создаёшь заказ можно совсем не указывать цвет самоката или можно указать один из цветов — BLACK или GREY или даже можно указать оба цвета")
    public void orderTest() {
        var order = OrderGenerator.generic();
        order.setColor(color);
        ValidatableResponse response = client.create(order);
        check.orderCreatedSuccessfully(response);
        Integer track = response.extract().body().path("track");
        client.cancel(track);
    }
}
