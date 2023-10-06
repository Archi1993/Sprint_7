package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import specifications.Client;

import java.util.Map;

public class CourierClient extends Client {
    public static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }


    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return spec()
                .body(Map.of("id", courierId))
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then().log().all();
    }

}
