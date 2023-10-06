package courier;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;

public class CourierAssertion {

    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", is(true));
    }

    public void createdWithoutRequiredFields(ValidatableResponse responseBadRequest) {
        responseBadRequest
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    public void createdNotSuccessfully(ValidatableResponse responseConflict) {
        responseConflict
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    public void loggedNotSuccessfully(ValidatableResponse badLoginResponse) {
        badLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }


    public void loggedWithNonExistentPair(ValidatableResponse badLoginResponse) {
        badLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    public void deletedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK);
    }


}
