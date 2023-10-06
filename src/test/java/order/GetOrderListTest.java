package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class GetOrderListTest {
    private final OrderClient client = new OrderClient();
    private final OrderAssertion check = new OrderAssertion();


    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка успешного выполнения запроса и получение заполненного списка заказов")
    public void checklistOrderTest() {
        ValidatableResponse response = client.orderList();
        check.getOrderListSuccessfully(response);
    }
}
