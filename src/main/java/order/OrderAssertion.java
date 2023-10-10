package order;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;

public class OrderAssertion {
    public void orderCreatedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue());
    }

    public void getOrderListSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}
