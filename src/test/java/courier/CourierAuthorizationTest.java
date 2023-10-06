package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierAuthorizationTest {

    private final CourierClient client = new CourierClient();
    private final CourierAssertion check = new CourierAssertion();
    protected int courierId;
    private boolean flag;


    @Before
    public void setFlagAfter() {
        flag = true;
    }

    @Test
    @DisplayName("Авторизации курьера")
    @Description("Проверка успешной авторизации курьера")
    public void createAuthorization() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    @Description("При отсутствии логина курьера в запросе на авторизацию возвращаем ошибку 'Недостаточно данных для входа' с кодом ошибки 400 - Bad Request")
    public void authorizationCourierWithoutLogin() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var inCompleteCreds = Credentials.fromWithoutLogin(courier);
        ValidatableResponse badLoginResponse = client.login(inCompleteCreds);
        check.loggedNotSuccessfully(badLoginResponse);
        var completeCreds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(completeCreds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без пароля")
    @Description("При отсутствии пароля курьера в запросе на авторизацию возвращаем ошибку 'Недостаточно данных для входа' с кодом ошибки 400 - Bad Request")
    public void authorizationCourierWithoutPassword() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var inCompleteCreds = Credentials.fromWithoutPassword(courier);
        ValidatableResponse badLoginResponse = client.login(inCompleteCreds);
        check.loggedNotSuccessfully(badLoginResponse);
        var completeCreds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(completeCreds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @Test
    @DisplayName("Тестирование авторизации курьера с несуществующей парой логин-пароль. Несуществующий логин")
    @Description("При несуществующем логине в паре логин-пароль у курьера в запросе на авторизацию возвращаем ошибку 'Учетная запись не найдена' с кодом ошибки 404 - Not Found")
    public void authorizationCourierWithNonExistentPairLogin() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var onExistentCreds = Credentials.fromNonExistentLogin(courier);
        ValidatableResponse badLoginResponse = client.login(onExistentCreds);
        check.loggedWithNonExistentPair(badLoginResponse);
        var completeCreds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(completeCreds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @Test
    @DisplayName("Тестирование авторизации курьера с несуществующей парой логин-пароль. Несуществующий пароль")
    @Description("При несуществующем пароле в паре логин-пароль у курьера в запросе на авторизацию возвращаем ошибку 'Учетная запись не найдена' с кодом ошибки 404 - Not Found")
    public void authorizationCourierWithNonExistentPairPassword() {
        var courier = CourierGenerator.random();
        ValidatableResponse response = client.create(courier);
        check.createdSuccessfully(response);
        var onExistentCreds = Credentials.fromNonExistentPassword(courier);
        ValidatableResponse badLoginResponse = client.login(onExistentCreds);
        check.loggedWithNonExistentPair(badLoginResponse);
        var completeCreds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(completeCreds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assert courierId != 0;
    }

    @After
    public void deleteCourier() {
        if (flag) {
            ValidatableResponse delete = client.delete(courierId);
            check.deletedSuccessfully(delete);
        }
    }

}
