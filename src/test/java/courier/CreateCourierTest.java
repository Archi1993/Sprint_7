package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierAssertion check = new CourierAssertion();
    protected int courierId;
    private boolean flag;


    @Before
    public void setFlagAfter() {
        flag = true;
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка успешного создания курьера c рандомным логином, затем логином на сайт и получением id курьера и последующем удалении курьера по id")
    public void createCourier() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @Test
    @DisplayName("Создание курьера с одинаковым логином")
    @Description("Если логин, переданный в запросе не уникален, то возвращаем ошибку 'Этот логин уже используется' с кодом ошибки 409 - Сonflict")
    public void createCourierClone() {
        var courier = CourierGenerator.generic();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
        ValidatableResponse responseConflict = client.create(courier);
        check.createdNotSuccessfully(responseConflict);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("При отсутствии логина курьера в запросе возвращаем ошибку 'Недостаточно данных для создания учетной записи' с кодом ошибки 400 - Bad Request")
    public void createCourierWithoutLogin() {
        var courier = CourierGenerator.genericWithoutLogin();
        ValidatableResponse response = client.create(courier);
        check.createdWithoutRequiredFields(response);
        flag = false;
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("При отсутствии пароля курьера в запросе возвращаем ошибку 'Недостаточно данных для создания учетной записи' с кодом ошибки 400 - Bad Request")
    public void createCourierWithoutPassword() {
        var courier = CourierGenerator.genericWithoutPassword();
        ValidatableResponse response = client.create(courier);
        check.createdWithoutRequiredFields(response);
        flag = false;
    }

    @After
    public void deleteCourier() {
        if (flag) {
            ValidatableResponse delete = client.delete(courierId);
            check.deletedSuccessfully(delete);
        }
    }

}

