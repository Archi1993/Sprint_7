package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import specifications.Client;


public class OrderClient extends Client {
    public static final String ORDER_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancel(int track) {
        return spec()
                .queryParam("track", track) //изменил ручку на актуальную, так как на той которая в спецификации не работает
                .when()
                .put(ORDER_PATH + "/cancel")
                .then().log().all();
    }

    @Step("Список заказов")
    public ValidatableResponse orderList() {
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}
